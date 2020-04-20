package roombook.model.room;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ROOM")
public class Room
{
    @JsonIgnore
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROOM_SEQ")
    @SequenceGenerator(name = "ROOM_SEQ", sequenceName = "ROOM_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "ROOMNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String roomName;

    @Column(name = "SEATS")
    @NotNull
    private Integer seats;

    @JsonIgnore
    @Column(name = "ACTIVATED")
    @NotNull
    private boolean activated;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public Integer getSeats()
    {
        return seats;
    }

    public void setSeats(Integer seats)
    {
        this.seats = seats;
    }

    public boolean isActivated()
    {
        return activated;
    }

    public void setActivated(boolean activated)
    {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return id.equals(room.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        return "Room{" +
                "roomName='" + roomName + "'" +
                ", seats=" + seats +
                ", activated=" + activated +
                "}";
    }
}