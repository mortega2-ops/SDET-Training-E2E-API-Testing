package io.catalyte.restAssured;

public class Todo {
    private String title;

    private boolean completed;

    private String id;

  public Todo(String title, Boolean completed, String id) {
    this.title = title;
    this.completed = completed;
    this.id = id;
  }

  @Override
  public String toString() {
    return "{\n" +
        "    \"title\": " + String.format("\"%s\"", title) + ",\n" +
        "    \"completed\": " + completed + ",\n" +
        "    \"id\": " + String.format("\"%s\"", id) + "\n" +
        '}';
  }
}
