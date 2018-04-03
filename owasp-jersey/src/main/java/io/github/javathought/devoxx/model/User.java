package io.github.javathought.devoxx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.security.auth.Subject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

/**
 *
 */
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Principal {

    private long id;
//    @XmlTransient
    private UUID uuid;
    private String name;
    @XmlTransient
    private String key;
//    private String firstname;
    private List<Role> roles;


    public User() {
        // Needed for JSON deserialisation
    }

    public User(long id,
                UUID uuid,
                String name,
                String key) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.key = getHash(key);
        this.roles = new ArrayList<>();
    }

    public User(long id,
                UUID uuid,
                String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.roles = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return "";
    }

    @JsonProperty
    public void setKey(String key) {
        if (key.isEmpty()) {
            this.key = "";
        } else {
            this.key = getHash(key);
        }
    }

    @Override
    @XmlTransient
    @JsonIgnore
    public boolean implies(Subject subject) {
        return false;
    }

    @XmlTransient
    @JsonIgnore
    public boolean isInRole(String s) {
        return roles.stream().anyMatch(r -> r.getRole().equals(s));
    }

/*
    public void setRoles(String[] profiles) {
        this.roles = Arrays.stream(profiles).map(s -> new Role(s)).collect(Collectors.toList());
    }
*/

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("uuid", uuid)
                .append("name", name)
                .toString();
    }

    public static String getHash(String password) {
        return DigestUtils.sha1Hex(password);
    }

}
