package project.main;

import java.sql.SQLException;
import spark.ModelAndView;
import spark.Spark;

import java.util.HashMap;
import project.dao.BookDao;
import project.dao.DatabaseDao;
import project.dao.VideoDao;
import project.database.Database;
import project.database.DatabaseImp;

import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    static Database db;

    public static void main(String[] args) throws SQLException {
        Database db;
        System.out.println("Hello world");
        if(System.getenv("JDBC_DATABASE_URL") != null){
            db = new DatabaseImp(System.getenv("JDBC_DATABASE_URL"));
        } else {
            db = new DatabaseImp("jdbc:sqlite:lukuvinkki.db");
        }

        BookDao bookDao = new BookDao(db);
        VideoDao videoDao = new VideoDao(db);
        DatabaseDao dbDao = new DatabaseDao(bookDao, videoDao);
        
        Spark.get("/", (req, res) -> {
            HashMap data = new HashMap<>();

            return new ModelAndView(data, "index");
        }, new ThymeleafTemplateEngine());

        
        Spark.get("/allTips", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("tips", dbDao.listAllTypes());
            
            return new ModelAndView(map, "tipList");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.post("/newBook", (req, res) -> {
            bookDao.add(req.queryParams("booktitle"), req.queryParams("bookauthor"), req.queryParams("ISBN"), req.queryParams("bookdescription"), req.queryParams("bookurl"));
            res.redirect("/allTips");
            return "New book added";
        });
        
        Spark.post("/newVideo", (req, res) -> {
            videoDao.add(req.queryParams("videotitle"), req.queryParams("videoauthor"), req.queryParams("videodescription"), req.queryParams("videourl"));
            res.redirect("/allTips");
            return "New video added";
        });
    }

}
