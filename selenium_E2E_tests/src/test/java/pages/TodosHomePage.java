package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TodosHomePage {

  @FindBy(className = "destroy")
  public static WebElement destroyButton;

  @FindBy(className = "toggle")
  public static WebElement checkBox;

  @FindBy(xpath = "//ul/li/div/label")
  public static WebElement label;

  @FindBy(className = "new-todo")
  public static WebElement newTodo;

  @FindBy(className = "todo")
  public static List<WebElement> todos;

  @FindBy(className = "todo-list")
  public static List<WebElement> todosSizeList;

  final WebDriver driver;
  final Actions actions;

  public TodosHomePage(WebDriver driver) {
    this.driver = driver;
    actions = new Actions(driver);
    PageFactory.initElements(this.driver, this);
  }


  /**
   * Method to add new to-do item to the list
   *
   * @param todoItem - String to-do item to add to to-do page
   */
  public void addNewTodo(String todoItem) {
    newTodo.sendKeys(todoItem);
    newTodo.sendKeys(Keys.ENTER);
  }

  /**
   * Method to click/complete the checkbox on the first to-do item in the list
   */
  public void completeTodo() {
    actions.moveToElement(checkBox).perform();
    actions.click().perform();
  }

  /**
   * Method to remove all todos currently displayed on the todos page. Called in @Before method
   */
  public void removeAllTodos() {
    for(WebElement todo : todos) {
      WebElement currentLabel = todo.findElement(By.tagName("label"));
      actions.moveToElement(currentLabel).perform();
      actions.click(currentLabel).perform();
      actions.moveToElement(destroyButton).perform();
      actions.click(destroyButton).perform();
    }
  }

  /**
   * Method to remove the first to-do in the list
   */
  public void removeFirstTodo() {
    actions.moveToElement(label).perform();
    actions.click(label).perform();
    actions.moveToElement(destroyButton).perform();
    actions.click(destroyButton).perform();
  }

  /**
   * Method to check whether the a String to-do has been displayed on the to-do page
   * @param todo - The String to check for
   * @return boolean
   */
  public boolean stringIsDisplayed(String todo) {
    return driver.getPageSource().contains(todo);
  }

  /**
   * Method to ensure that a to-do is completed and has a line through it.
   * @return boolean
   */
  public boolean todoHasLineThroughIt() {
    return label.getCssValue("text-decoration").contains("line-through");
  }

  public List<String> todosToString() {
    List<String> todosAsStrings = new ArrayList<>();
    for (WebElement todo : todos) {
      todosAsStrings.add(todo.getText());
    }
    return todosAsStrings;
  }


}
