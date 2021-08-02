package uk.robevans.github;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class Graphql {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "https://api.github.com/graphql";
        final String queryString = "\"query listRepos($queryString: String!) {"
                + " rateLimit {"
                + "  cost"
                + "  remaining"
                + "  resetAt"
                + " }"
                + " search(query:$queryString, type:REPOSITORY, first:50){  "
                + "  repositoryCount"
                + "  pageInfo{"
                + "   endCursor"
                + "   startCursor"
                + "  }"
                + "  edges{"
                + "   node{"
                + "    ... on Repository{"
                + "     id"
                + "     name"
                + "     createdAt "
                + "     description "
                + "     isArchived"
                + "     isPrivate"
                + "     url"
                + "     owner{"
                + "      login"
                + "      id"
                + "      __typename"
                + "      url"
                + "     }"
                + "     assignableUsers{"
                + "      totalCount"
                + "     }"
                + "     licenseInfo{"
                + "      key"
                + "     }"
                + "     defaultBranchRef{"
                + "      target{"
                + "       ... on Commit{"
                + "        history(first:10){"
                + "         totalCount"
                + "         edges{"
                + "          node{"
                + "           ... on Commit{"
                + "            committedDate"
                + "           }"
                + "          }"
                + "         }"
                + "        }"
                + "       }"
                + "      }"
                + "     }"
                + "    }"
                + "   }"
                + "  }"
                + " }"
                + "}\"";
        final String query = "{"
                + " \"variables\": {"
                + "    \"queryString\": \"is:public archived:false created:>2020-07-15 pushed:>2020-12-15\""
                + " },"
                + " \"query\": " + queryString + ","
                + " \"refOrder\": {"
                + "  \"direction\": \"DESC\","
                + "  \"field\": \"TAG_COMMIT_DATE\""
                + " }"
                + "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(System.getenv("GRAPHQL_TOKEN"));
        HttpEntity<String> entity = new HttpEntity<>(query, headers);

        final ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        System.out.println("Response: " + response.getBody());
    }
}
