package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pages.TodosHomePage.checkBox;
import static pages.TodosHomePage.todos;

import com.google.common.collect.ImmutableList;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import pages.TodosHomePage;

public class SeleniumTestsV2 {

  private static WebDriver driver;
  public static TodosHomePage TodosPageInst;
  public static String testTodo;
  public static final List<String> expectedLabelsList = ImmutableList
      .of("Read a book.", "Eat some vegetables.");
  /**
   * Adds three to-dos to the to-do page in order.
   */
  public void addTodos() {
    TodosPageInst.addNewTodo("Walk the dog.");
    TodosPageInst.addNewTodo("Read a book.");
    TodosPageInst.addNewTodo("Eat some vegetables.");
  }

  @Before
  public void beforeMethod() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    driver.get("http://localhost:3000");

    TodosPageInst = PageFactory.initElements(driver, TodosHomePage.class);
    TodosPageInst.removeAllTodos();
  }

  /**
   * Test 1) 1) GIVEN I am at the todo page WHEN I add text then press enter THEN It is added to the
   * list
   *
   * @throws InterruptedException
   */
  @Test
  public void testAddNewTodo() throws InterruptedException {
    testTodo = "Check this todo has been added.";
    TodosPageInst.addNewTodo(testTodo);
    Thread.sleep(1000);
    assertTrue(TodosPageInst.stringIsDisplayed(testTodo));
  }

  /**
   * Test 2) GIVEN I am at the todo page AND there is an item on the list WHEN I hover over the item
   * AND X out on the item THEN the item is removed
   *
   * @throws InterruptedException
   */
  @Test
  public void testRemoveTodo() throws InterruptedException {
    addTodos();
    Thread.sleep(1000);
    int totalNumberOfTodos = todos.size();
    TodosPageInst.removeFirstTodo();
    driver.navigate().refresh();
    assertEquals(totalNumberOfTodos - 1, todos.size());

    Thread.sleep(3000);
  }

  /**
   * Test 3) GIVEN I am at the todo page WHEN I click on the circle next to the item THEN the item
   * is crossed out AND the item is greyed out
   *
   * @throws InterruptedException
   */
  @Test
  public void testCompleteTodo() throws InterruptedException {
    addTodos();
    TodosPageInst.completeTodo();
    assertTrue(checkBox.isSelected());
    assertTrue(TodosPageInst.todoHasLineThroughIt());

    Thread.sleep(3000);
  }

  /**
   * Test 4) GIVEN I am at the todo page WHEN I hover over an item AND I click on the X next to the
   * item THEN the list will collapse AND not reorder the list
   *
   * @throws InterruptedException
   */
  @Test
  public void testRemoveItemAndEnsureOrderRemains() throws InterruptedException {
    addTodos();
    Thread.sleep(1000);
    TodosPageInst.removeFirstTodo();
    driver.navigate().refresh();
    assertEquals(expectedLabelsList, TodosPageInst.todosToString());
    Thread.sleep(3000);
  }

  @After
  public void afterMethod() {
    driver.quit();
  }
}
