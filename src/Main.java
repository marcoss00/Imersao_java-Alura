import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        var response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // pegar só os dados que interessam (título, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaFilmes = parser.parse(body);

        // exibir e manipular os dados
        for (Map<String, String> filme : listaFilmes) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));
            System.out.println(filme.get("imDbRating"));
            System.out.println();
        }

        /*
         DESAFIOS
         1 - Consumir o endpoint de filmes mais populares da API do IMDB. Procure também, na documentação da API do IMDB,
          o endpoint que retorna as melhores séries e o que retorna as séries mais populares.

         2 - Usar sua criatividade para deixar a saída dos dados mais bonitinha: usar emojis com código UTF-8, mostrar a nota do filme
          como estrelinhas, decorar o terminal com cores, negrito e itálico usando códigos ANSI, e mais!

         3 - Colocar a chave da API do IMDB em algum lugar fora do código como um arquivo de configuração (p. ex, um arquivo .properties)
          ou uma variável de ambiente.

         4 - Mudar o JsonParser para usar uma biblioteca de parsing de JSON como Jackson ou GSON.

         5 - Desafio supremo: criar alguma maneira para você dar uma avaliação ao filme, puxando de algum arquivo de configuração
          OU pedindo a avaliação para o usuário digitar no terminal.
         */

        Properties prop = LerApi.getProp();

        String apiKey = prop.getProperty("key");
        String url2 = "https://api.mocki.io/v2/" + apiKey;
        URI endereco2 = URI.create(url2);
        var client2 = HttpClient.newHttpClient();
        var request2 = HttpRequest.newBuilder(endereco2).GET().build();
        var response2 = client2.send(request2, BodyHandlers.ofString());
        String body2 = response2.body();

        //JsonParserGson parser2 = new JsonParserGson();
        JsonParser parser2 = new JsonParser();
        List<Map<String, String>> listaFilmes2 = parser2.parse(body2);

        int indexFilme = 1;
        for (Map<String, String> filme : listaFilmes2) {
            System.out.println(indexFilme + " - " + filme.get("title"));
            indexFilme++;
        }
        Scanner ler = new Scanner(System.in);
        System.out.println("Escolha um dos 10 filmes listados: ");
        int filmeNumero = ler.nextInt() - 1;
        Map<String, String> filmeEscolhido = listaFilmes2.get(filmeNumero);
        System.out.println("Qual nota você dá para o filme " + filmeEscolhido.get("title") + " ?");
        int nota = ler.nextInt();

        for (Map<String, String> filme : listaFilmes2) {
            System.out.println("\u001b[3m Titulo:\u001b[m" + "\u001b[97;1m \u001b[42;1m " + filme.get("title") + " \u001b[m");
            System.out.println("\u001b[3m Poster:\u001b[m" + filme.get("image"));
            System.out.println("\u001b[3m Nota:\u001b[m" + "\u001b[97;1m \u001b[45m " + filme.get("imDbRating") + " \u001b[m");
            double estrelas = Double.parseDouble(filme.get("imDbRating"));
            int estrelas2;
            if(Objects.equals(filme.get("title"), filmeEscolhido.get("title"))) {
                estrelas2 = nota;
            }
            else {
                estrelas2 = (int) estrelas;
            }
            for (int i = 0; i<estrelas2; i++){
                System.out.print("\u2B50");
            }
            System.out.println();
            System.out.println();
        }
    }
}