package roombook.room;

import org.json.simple.JSONObject;

public abstract class Room
{
    private String name;
    private Integer seats;

    public Room()
    {
        this.setName(null);
        this.setSeats(null);
    }

    public Room(String name, int seats)
    {
        this.setName(name);
        this.setSeats(seats);
    }

    public abstract String getType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String toJSONString()
    {
        JSONObject obj = new JSONObject();

        obj.put("name", this.name);
        obj.put("seats", this.seats);

        return obj.toJSONString();
    }
}