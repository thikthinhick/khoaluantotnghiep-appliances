package com.vnu.demo.controller;

import com.vnu.demo.service.ScheduleTaskFixedRate;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appliance")
public class ApplianceController {
    @Autowired
    private ScheduleTaskFixedRate scheduleTaskFixedRate;
    @PostMapping("/change_status")
    public void updateStatusAppliance(@RequestBody RequestForm requestForm) {
        scheduleTaskFixedRate.changeStatusAppliance(requestForm.applianceId, requestForm.getStatus());
    }
    @PostMapping("/change_status_all")
    public void updateStatusAllAppliance(@RequestBody RequestForm requestForm) {
        scheduleTaskFixedRate.changeStatusAllAppliance(requestForm.getRoomId());
    }
    @PutMapping
    public ResponseEntity<?> updateStandbyAppliance(@RequestBody RequestForm requestForm, @RequestParam("id") Long id) {
        scheduleTaskFixedRate.changeStandbyAppliance(id, requestForm.getStandby());
        return ResponseEntity.ok().body("Cập nhật thành công!");
    }
    @PostMapping("/change_auto_off")
    public void updateAutoOffAppliance(@RequestBody RequestForm requestForm) {
        scheduleTaskFixedRate.changeAutoOffAppliance(requestForm.getApplianceId(), requestForm.getAutoOff());
    }
    @GetMapping("/add_appliance")
    public void addAppliance(@RequestParam("id") Long id) {
        scheduleTaskFixedRate.addAppliance(id);
    }
    @GetMapping("/delete_appliance")
    public void deleteAppliance(@RequestParam("id") Long id) {
        scheduleTaskFixedRate.deleteAppliance(id);
    }
    @Data
    private static class RequestForm{
        private Boolean status;
        private Long applianceId;
        private Boolean standby;
        private Boolean autoOff;
        private Long roomId;
    }
}
