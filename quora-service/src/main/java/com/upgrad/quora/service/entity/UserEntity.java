package com.upgrad.quora.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * User entity
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "userByUserName",
                query = "select u from UserEntity u where u.userName=:userName"),
        @NamedQuery(name = "userByEmail", query = "select u from UserEntity u where u.email=:email"),
        @NamedQuery(name = "userByUserId", query = "select u from UserEntity u where u.uuid=:userId"),
        @NamedQuery(name = "getUserByUserId", query = "select u from UserEntity u where u.uuid = :userId"),
        @NamedQuery(name = "deleteUserByUserId", query = "delete from UserEntity u where u.uuid = :userId")
})
public class UserEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "firstname")
    @NotNull
    @Size(max = 30)
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    @Size(max = 30)
    private String lastName;

    @Column(name = "username")
    @NotNull
    @Size(max = 30)
    private String userName;

    @Column(name = "email")
    @NotNull
    @Size(max = 50)
    private String email;

    // @ToStringExclude
    @Column(name = "password")
    @NotNull
    @Size(max = 255)
    private String password;

    // @ToStringExclude
    @Column(name = "salt")
    @NotNull
    @Size(max = 200)
    private String salt;

    @Column(name = "country")
    @Size(max = 30)
    private String country;

    @Column(name = "aboutme")
    @Size(max = 50)
    private String aboutMe;

    @Column(name = "dob")
    @Size(max = 30)
    private String dob;

    @Column(name = "role")
    @Size(max = 30)
    private String role;

    @Column(name = "contactnumber")
    @Size(max = 30)
    private String contactNumber;

    /**
     * Gets id *
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id *
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets uuid *
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets uuid *
     *
     * @param uuid uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets first name *
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name *
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name *
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name *
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets user name *
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name *
     *
     * @param userName user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets email *
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email *
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password *
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password *
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets salt *
     *
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets salt *
     *
     * @param salt salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Gets country *
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country *
     *
     * @param country country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets about me *
     *
     * @return the about me
     */
    public String getAboutMe() {
        return aboutMe;
    }

    /**
     * Sets about me *
     *
     * @param aboutMe about me
     */
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    /**
     * Gets dob *
     *
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * Sets dob *
     *
     * @param dob dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * Gets role *
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets role *
     *
     * @param role role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets contact number *
     *
     * @return the contact number
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * Sets contact number *
     *
     * @param contactNumber contact number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
