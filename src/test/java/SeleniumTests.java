import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class SeleniumTests {

  public static WebDriver driver;

  @Before
  public void setupClass() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
  }

  @Test
  public void TestOneAddItemToTodoList() throws InterruptedException, NotFoundException {
    // A unique random string that will be added to the todo_list each time this test runs.
    String randomTodoItem = randomUUID().toString();
    try {
      driver.get("http://localhost:3000");
      driver.findElement(By.id("add-todo")).sendKeys(randomTodoItem + Keys.ENTER);
      Thread.sleep(1000); //Wait for the string to be visible
      assertTrue(driver.getPageSource().contains(randomTodoItem));
    } finally {
      driver.quit();
    }
  }

  @Test
  public void TestTwoRemoveItemFromTodoList() throws InterruptedException, NotFoundException {
    // A unique random string that will be added to the todo_list each time this test runs
    String randomTodoItem = randomUUID().toString();
    try {
      driver.get("http://localhost:3000");
      driver.manage().window().maximize();

      // Add an item to the to-do list and ensure it exists
      WebElement todoList = driver.findElement(By.id("add-todo"));
      todoList.sendKeys(randomTodoItem + Keys.ENTER);
      Thread.sleep(1000);// Wait for the string to be visible
      assertTrue(driver.getPageSource().contains(randomTodoItem));

      //Grab all the items in the to-do list and search them for the one that was just added
      ArrayList<WebElement> todos = new ArrayList<>(driver.findElements(By.className("todo")));
      for (WebElement todo : todos) {
        if (todo.getText().equals(randomTodoItem)) {
          //Make the button appear by hovering and clicking on the element
          Actions action = new Actions(driver);
          action.moveToElement(todo).perform();
          action.click(todo).perform();

          //Find and click the element that has appeared to destroy it
          WebElement destroyButton = todo.findElement(By.className("destroy"));
          action.moveToElement(destroyButton).perform();
          action.click(destroyButton).perform();

          //Wait for user to notice the event
          Thread.sleep(3000);
        }
      }
    } finally {
      driver.quit();
    }
  }

  @Test
  public void testThreeCheckBoxAndEnsureTodoCompleted()
      throws InterruptedException, NotFoundException {
    // A unique random string that will be added to the todo_list each time this test runs
    String randomTodoItem = randomUUID().toString();
    try {
      driver.get("http://localhost:3000");
      driver.manage().window().maximize();

      // Add an item to the to-do list and ensure it exists
      WebElement todoList = driver.findElement(By.id("add-todo"));
      todoList.sendKeys(randomTodoItem + Keys.ENTER);
      Thread.sleep(1000);// Wait for the string to be visible
      assertTrue(driver.getPageSource().contains(randomTodoItem));

      //Grab all the items in the to-do list and search them for the one that was just added
      ArrayList<WebElement> todos = new ArrayList<>(driver.findElements(By.className("todo")));
      for (WebElement todo : todos) {
        if (todo.getText().equals(randomTodoItem)) {
          //Make the button appear by hovering and clicking on the element
          Actions action = new Actions(driver);
          WebElement checkBox = todo.findElement(By.className("toggle"));
          action.moveToElement(todo).perform();
          action.moveToElement(checkBox).perform();
          action.click(checkBox).perform();

          //Wait for user to notice event
          Thread.sleep(3000);
        }
      }

      //Grab all the items in the to-do list that are marked as completed and ensure the random string is there.
      ArrayList<WebElement> completedTodos = new ArrayList<>(
          driver.findElements(By.cssSelector("li[class='todo completed']")));
      boolean isCompleted = false;
      for (WebElement completedTodo : completedTodos) {
        System.out.println(completedTodo.getText());
        if (completedTodo.getText().equals(randomTodoItem)) {
          System.out.println(completedTodo.getText());
          isCompleted = true;
        }
      }
      assertTrue(isCompleted);

    } finally {
      driver.quit();
    }
  }

  @Test
  public void TestFourListItemDeletedAndListOrderRemains()
      throws InterruptedException, NotFoundException {
    //Static string items that will be used to test the list in case no items already present
    String itemOne = "Todo item 1";
    String itemTwo = "Todo item 2";
    String itemThree = "Todo item 3";
    // A unique random string that will be added to the todo_list each time this test runs
    String randomTodoItem = randomUUID().toString();
    try {
      driver.get("http://localhost:3000");
      driver.manage().window().maximize();

      // Add static items to the to-do list
      WebElement todoList = driver.findElement(By.id("add-todo"));
      todoList.sendKeys(itemOne + Keys.ENTER);
      todoList.sendKeys(itemTwo + Keys.ENTER);
      todoList.sendKeys(itemThree + Keys.ENTER);
      Thread.sleep(1000);// Wait for the string to be visible
      assertTrue(driver.getPageSource().contains(itemOne));
      assertTrue(driver.getPageSource().contains(itemTwo));
      assertTrue(driver.getPageSource().contains(itemThree));
      //Grab all the static items in the to-do plus any that were there and save them for later check
      ArrayList<WebElement> staticTodos = new ArrayList<>(driver.findElements(By.className("todo")));

      //Add the unique random to-do item sotre in list and ensure it is not the same as the staticList
      todoList.sendKeys(randomTodoItem + Keys.ENTER);
      Thread.sleep(1000);
      assertTrue(driver.getPageSource().contains(randomTodoItem));
      ArrayList<WebElement> allTodos = new ArrayList<>(driver.findElements(By.className("todo")));
      assertFalse(staticTodos.equals(allTodos));

      //Remove the random to-do item
      for (WebElement todo : allTodos) {
        if (todo.getText().equals(randomTodoItem)) {
          //Make the button appear by hovering and clicking on the element
          Actions action = new Actions(driver);
          action.moveToElement(todo).perform();
          action.click(todo).perform();

          //Find and click the element that has appeared to destroy it
          WebElement destroyButton = todo.findElement(By.className("destroy"));
          action.moveToElement(destroyButton).perform();
          action.click(destroyButton).perform();

          //Wait for user to notice the event
          Thread.sleep(3000);
        }
      }
      //Research and reset all to-dos the check that it is now the same as the staticList
      allTodos = new ArrayList<>(driver.findElements(By.className("todo")));
      assertTrue(staticTodos.equals(allTodos)); // equals checks for both order, and elements
    } finally {
      driver.quit();
    }
  }


}
