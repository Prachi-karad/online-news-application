package com.prachi.news.config;

import com.prachi.news.entity.Article;
import com.prachi.news.entity.Category;
import com.prachi.news.repository.ArticleRepository;
import com.prachi.news.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/** Inserts sample categories and articles on first start (only when the database is empty). */
@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public DataLoader(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) return;

        List<Category> c = categoryRepository.saveAll(List.of(
                new Category("India", "National news"),
                new Category("Technology", "Gadgets, software and startups"),
                new Category("Sports", "Cricket, football and more"),
                new Category("Business", "Markets and economy"),
                new Category("Entertainment", "Movies, music and OTT")
        ));

        articleRepository.saveAll(List.of(
                article("Monsoon brings relief to Marathwada farmers",
                        "Good rainfall this season has filled reservoirs across Nanded, Latur and Beed districts.",
                        "Farmers in the Marathwada region are hopeful after steady monsoon rains filled several major reservoirs.\n\n"
                                + "Officials said water storage is significantly higher than last year, which should help the rabi crop season.\n\n"
                                + "Agriculture experts advise farmers to plan sowing based on soil moisture and local forecasts.",
                        "Staff Reporter", c.get(0), true, "rain"),
                article("Pune emerges as a top hub for Java developers",
                        "IT companies in Pune are expanding hiring for Spring Boot and microservices roles.",
                        "Recruiters say demand for backend developers skilled in Java, Spring Boot and cloud platforms remains strong.\n\n"
                                + "Freshers with solid project portfolios on GitHub are getting more interview calls, according to placement trainers.\n\n"
                                + "Skills in REST API design, SQL and React are frequently listed together in full stack job descriptions.",
                        "Tech Desk", c.get(1), true, "code"),
                article("India clinch thrilling last-over win in T20 series opener",
                        "A late batting surge sealed a four-wicket victory in front of a packed stadium.",
                        "Chasing a target of 182, India needed 14 runs off the final over and got there with a ball to spare.\n\n"
                                + "The middle order rebuilt the innings after early wickets, and the finisher struck two sixes in the last over.\n\n"
                                + "The second match of the series will be played on Sunday.",
                        "Sports Desk", c.get(2), true, "cricket"),
                article("Small businesses turn to UPI for everyday payments",
                        "Digital payments continue to grow in tier-2 and tier-3 cities.",
                        "Shop owners report that most customers now prefer paying with UPI for small purchases.\n\n"
                                + "Bankers say this shift is helping small businesses build a transaction history for loans.",
                        "Business Bureau", c.get(3), false, "market"),
                article("Regional cinema shines at the box office this festive season",
                        "Marathi and other regional films are drawing large audiences in theaters.",
                        "Distributors say strong word of mouth has kept regional films running for weeks.\n\n"
                                + "Several films are also getting OTT releases soon after their theatrical run.",
                        "Entertainment Desk", c.get(4), false, "cinema"),
                article("New AI tools help students learn programming faster",
                        "Educators are adding AI assistants to coding courses.",
                        "Colleges are experimenting with AI-powered tools that explain errors and suggest fixes.\n\n"
                                + "Teachers say the key is to use them for understanding concepts, not just copying answers.",
                        "Tech Desk", c.get(1), false, "laptop"),
                article("Football league kicks off with record attendance",
                        "Opening weekend saw packed stands across all venues.",
                        "Organisers reported record ticket sales for the opening weekend of the new season.\n\n"
                                + "Young Indian players impressed in several matches.",
                        "Sports Desk", c.get(2), false, "football")
        ));
    }

    private Article article(String title, String summary, String content, String author,
                            Category category, boolean featured, String imageSeed) {
        Article a = new Article();
        a.setTitle(title);
        a.setSummary(summary);
        a.setContent(content);
        a.setAuthor(author);
        a.setCategory(category);
        a.setFeatured(featured);
        a.setImageUrl("https://picsum.photos/seed/" + imageSeed + "/800/450");
        return a;
    }
}
