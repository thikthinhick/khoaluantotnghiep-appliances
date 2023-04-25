package com.vnu.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties("room")
public class Appliance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String thumbnail;
    private Boolean category;
    private Long room_id;
    private Boolean status;
    private Boolean autoOff;
    @Transient
    private Boolean standby = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Boolean getCategory() {
        return category;
    }

    public void setCategory(Boolean category) {
        this.category = category;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStandby() {
        return standby;
    }

    public void setStandby(Boolean standby) {
        this.standby = standby;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public Boolean getAutoOff() {
        return autoOff;
    }

    public void setAutoOff(Boolean autoOff) {
        this.autoOff = autoOff;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }
}
