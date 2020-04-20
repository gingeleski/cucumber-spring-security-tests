package roombook.model.appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "APPOINTMENT")
public class Appointment
{
    @JsonIgnore
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPOINTMENT_SEQ")
    @SequenceGenerator(name = "APPOINTMENT_SEQ", sequenceName = "APPOINTMENT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    @Column(name = "ROOM_ROOMNAME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String roomName;

    @Column(name = "OWNER_USERNAME", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "TIME_START")
    @NotNull
    private Timestamp start;

    @Column(name = "TIME_END")
    @NotNull
    private Timestamp end;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Timestamp getStart()
    {
        return start;
    }

    public void setStart(Timestamp start)
    {
        this.start = start;
    }

    public Timestamp getEnd()
    {
        return end;
    }

    public void setEnd(Timestamp end)
    {
        this.end = end;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Appointment appt = (Appointment) o;

        return id.equals(appt.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        return "Appointment{" +
                "name='" + name + "'" +
                ", roomName='" + roomName + "'" +
                ", username='" + username + "'" +
                ", start='" + start + "'" +
                ", end='" + end + "'" +
                "}";
    }
}
