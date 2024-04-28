package AddProductToCart;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AddProductToCart extends Parameters {

	@BeforeTest
	public void setup() {
		driver.get(url);
		driver.manage().window().maximize();
	}
	
	@Test(invocationCount = 1)
	public void addProduct() throws InterruptedException, IOException {
		WebElement searchInput = driver.findElement(By.id("js-site-search-input"));
		searchInput.sendKeys("iphone");
		searchInput.sendKeys(Keys.ENTER);
		
		
		WebElement PriceFilterList = driver.findElement(By.cssSelector("div[class='facet js-facet head_price'] div[class='facet__name js-facet-name']"));
		PriceFilterList.click();
		
		WebElement priceListContainer = driver.findElement(By.cssSelector("body > main:nth-child(10) > div:nth-child(12) > div:nth-child(1) > div:nth-child(6) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > ul:nth-child(1)"));
		List<WebElement> priceList = priceListContainer.findElements(By.tagName("li"));
		
		priceList.get(priceList.size()-1).click();
		
		WebElement productContainer = driver.findElement(By.cssSelector(".product__listing.product__grid"));
		List<WebElement> products = productContainer.findElements(By.xpath("//div[@class='product-item item_grid']"));
		
		
		Thread.sleep(1000);
		
		for(int i=0 ; i < products.size() ; i++) {
			
			int randomNumber = random.nextInt(products.size());
			WebElement productChoosed = products.get(randomNumber);
			
		
		    String priceText = productChoosed.findElement(By.className("price")).getText();
			System.out.println(randomNumber);
			
			if(priceText == "") {
				System.out.println("not Found ");
				continue;
			}else {
				String cleanedAmountString = priceText.replace(",", "").replace(" JOD", ""); // Removes comma and " JOD"
				int price = Integer.parseInt(cleanedAmountString);
				//Check If Price Less Than 1100 JOD
				if(price < 1100) {
					productChoosed.click();
					System.out.println("less than 1100");
					break;
				}else {
					continue;
				}
			}		
			
		}
		
		//Assertion
		WebElement addToCartButton = driver.findElement(By.id("addToCartButton"));
		System.out.println(addToCartButton.getText());
		Boolean actualResult = addToCartButton.getText().contains("ADD TO CART");
		myAssert.assertEquals(actualResult, true , "The Product is Sold Out");
		
		addToCartButton.click();
		
		Thread.sleep(2000);
		TakeScreenshot();
		
	}
}
