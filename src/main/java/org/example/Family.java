package org.example;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Family {
    @SerializedName("familyName")
    private String familyName;

    @SerializedName("members")
    private List<Person> members;

    // Getter und Setter
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }
}
