package com.vnu.demo.service;

import com.vnu.demo.common.RandomData;
import com.vnu.demo.entity.Appliance;
import com.vnu.demo.model.Detail;
import com.vnu.demo.model.MessageConsumption;
import com.vnu.demo.redis.MessagePublisher;
import com.vnu.demo.repository.ApplianceRepository;
import com.vnu.demo.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduleTaskFixedRate {
    private final ApplianceRepository applianceRepository;
    private List<Appliance> applianceList;

    @PostConstruct
    private void init() {
        applianceList = applianceRepository.findAll();
    }

    private final MessagePublisher messagePublisher;

    public void changeStatusAppliance(Long applianceId, Boolean status) {
        Appliance appliance = applianceRepository.findById(applianceId).orElseThrow(() -> new RuntimeException("Không tìm thấy thiết bị!"));
        appliance.setStatus(status);
        applianceRepository.save(appliance);
        applianceList.forEach(element -> {
            if (Objects.equals(element.getId(), applianceId)) {
                element.setStatus(status);
            }
        });
    }
    public void changeStatusAllAppliance(Long roomId) {
        List<Appliance> appliances = applianceRepository.findAll();
        appliances.forEach(element -> {
            if(Objects.equals(element.getRoom_id(), roomId)) {
                element.setStatus(false);
            }
        });
        applianceRepository.saveAll(appliances);
        applianceList.forEach(element -> {
            if (Objects.equals(element.getRoom_id(), roomId)) {
                element.setStatus(false);
            }
        });
    }
    public void changeAutoOffAppliance(Long applianceId, Boolean status) {
        Appliance appliance = applianceRepository.findById(applianceId).orElseThrow(() -> new RuntimeException("Không tìm thấy thiết bị!"));
        appliance.setAutoOff(status);
        applianceRepository.save(appliance);
        applianceList.forEach(element -> {
            if (Objects.equals(element.getId(), applianceId)) element.setAutoOff(status);
        });
    }

    public void changeStandbyAppliance(Long applianceId, Boolean standby) {
        applianceList.forEach(element -> {
            if (Objects.equals(element.getId(), applianceId)) {
                element.setStandby(standby);
                if(standby) {
                    sendNotificationStandbyAppliance(applianceId, false);
                }
            }
        });
    }
    public void addAppliance(Long applianceId) {
        Appliance appliance = applianceRepository.findById(applianceId).orElseThrow(() -> new RuntimeException("Không tìm thấy thiết bị!"));
        applianceList.add(appliance);
    }
    public void deleteAppliance(Long applianceId) {
        applianceList.forEach(appliance -> {
            if(Objects.equals(applianceId, appliance.getId())) {
                applianceList.remove(appliance);
            }
        });
    }
    public void sendNotificationStandbyAppliance(Long applianceId, Boolean off) {
        final String uri = "http://localhost:8081/api/appliance/" + applianceId + "/standby?off=" + off;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> responseEntity = restTemplate.getForEntity(uri, Object.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            log.info("send notification to server");
        }
    }

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        MessageConsumption messageConsumption = new MessageConsumption();
        String time = StringUtils.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
        messageConsumption.setTime(time);
        HashMap<Long, Detail> map = new HashMap<>();
        applianceList.forEach(value ->
        {
            if (value.getStatus()) {
                map.put(value.getId(), Detail.builder()
                        .roomId(value.getRoom_id())
                        .value(RandomData.get(50, 300))
                        .standBy(value.getStandby()
                        )
                        .build());
                if (value.getStandby() && value.getAutoOff()) {
                    changeStatusAppliance(value.getId(), false);
                    sendNotificationStandbyAppliance(value.getId(), true);
                }
            }
        });
        messageConsumption.setData(map);
        messagePublisher.publish(messageConsumption.toJson());
    }
}