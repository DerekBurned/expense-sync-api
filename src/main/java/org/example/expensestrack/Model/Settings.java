package org.example.expensestrack.Model;

import jakarta.persistence.*;
import java.util.Map;
import java.util.HashMap;

@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    private String name;
    private String icon;
    private String email;
    private Boolean darkTheme;

    @ElementCollection
    @CollectionTable(name = "settings_custom_categories", 
        joinColumns = @JoinColumn(name = "settings_id"))
    @MapKeyColumn(name = "category_key")
    @Column(name = "category_value")
    private Map<String, String> customCategories = new HashMap<>();

    // Default constructor required by JPA
    public Settings() {}

    // Constructor with arguments
    public Settings(String userId, String name, String icon, String email, Boolean darkTheme, Map<String, String> customCategories) {
        this.userId = userId;
        this.name = name;
        this.icon = icon;
        this.email = email;
        this.darkTheme = darkTheme;
        this.customCategories = customCategories;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getDarkTheme() { return darkTheme; }
    public void setDarkTheme(Boolean darkTheme) { this.darkTheme = darkTheme; }

    public Map<String, String> getCustomCategories() { return customCategories; }
    public void setCustomCategories(Map<String, String> customCategories) { this.customCategories = customCategories; }
}
