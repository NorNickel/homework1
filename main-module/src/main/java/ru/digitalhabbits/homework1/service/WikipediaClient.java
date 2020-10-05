package ru.digitalhabbits.homework1.service;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.digitalhabbits.homework1.wikimodel.Page;
import ru.digitalhabbits.homework1.wikimodel.Query;
import ru.digitalhabbits.homework1.wikimodel.Root;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";
    private static final Gson gson = new Gson();

    @Nonnull
    public String search(@Nonnull String searchString) {
        // TODO: Check

        String result = "";

        final URI uri = prepareSearchUrl(searchString);

        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {

            final HttpGet request = new HttpGet(uri);

            try (final CloseableHttpResponse response = httpClient.execute(request)) {

                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    final String json = EntityUtils.toString(entity);
                    result = convertWikiJsonToString(json);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Nonnull
    private String convertWikiJsonToString(@Nonnull String jsonText) {

        // TODO: Check

        String result = "";

        final Root root = gson.fromJson(jsonText, Root.class);
        final Query query = root.getQuery();
        final Map<String, Page> pages = query.getPages();

        for (Page page : pages.values()) {
            // now take just first result
            result = page.getExtract();
            break;
        }

        return result;
    }
}
