package org.example.cafepos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// EmployeeData.java (new class)
public class EmployeeData {
    private final IntegerProperty id;
    private final StringProperty username;
    private final StringProperty role;
    private final StringProperty question;
    private final StringProperty answer;

    public EmployeeData(int id, String username, String role, String question, String answer) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
        this.question = new SimpleStringProperty(question);
        this.answer = new SimpleStringProperty(answer);
    }

    // Getters and property methods...
    public int getId() { return id.get(); }
    public String getUsername() { return username.get(); }
    public String getRole() { return role.get(); }
    public String getQuestion() { return question.get(); }
    public String getAnswer() { return answer.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty roleProperty() { return role; }
}
