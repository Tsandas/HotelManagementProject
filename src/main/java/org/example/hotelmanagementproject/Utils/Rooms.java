package org.example.hotelmanagementproject.Utils;

public class Rooms {

    private int roomId;
    private String roomType;
    private String amenities;
    private boolean available;

    public Rooms(int roomId, String roomType, String amenities, boolean available) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.amenities = amenities;
        this.available = available;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getAmenities() {
        return amenities;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }


}
