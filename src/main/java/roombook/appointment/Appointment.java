package roombook.appointment;

import java.time.LocalTime;

public class Appointment
{
    private String name;
    private String room;
    private String owner;
    private String creator;
    private LocalTime start;
    private LocalTime end;

    public Appointment()
    {
        this.setName(null);
        this.setRoom(null);
        this.setOwner(null);
        this.setCreator(null);
        this.setStart(null);
        this.setEnd(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}