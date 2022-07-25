package Manga;

import us.codecraft.webmagic.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MangaUtils {

    private static final String NAME = "<title>\\S*";

    private static final String PAGENAME = "<h6.*/h6>";

    private static final String PAGETITLE = "<title>\\S*";

    public static String GetName(Page page) {
        String name = page.getHtml().regex(NAME).get().substring(7);
        return name;
    }
    public static void MangaDirMake(Page page) {
        String name = GetName(page);
        Path path = Paths.get("/Users/yangpixi/Documents/Manga/" + name);
        try {
            Path pathCreat = Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String PageDirMake(Page page, String MangaName) {
        if (page.getHtml().regex(PAGENAME).match()) {
            String PageName_get = page.getHtml().regex(PAGENAME).get();
            String PageTitle_get = page.getHtml().regex(PAGETITLE).get();
            String PageName = PageName_get.substring(4, PageName_get.length() - 5);
            String PageTitle = PageTitle_get.substring(7);
            Path path = Paths.get("/Users/yangpixi/Documents/Manga/" + MangaName + "/" + PageTitle + "_" + PageName);
            try {
                Path pathCreat = Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return "/Users/yangpixi/Documents/Manga/" + MangaName + "/" + PageTitle + "_" +PageName + "/";
        }
        else {
            System.out.println("Failue");
            return null;
        }
    }
}
