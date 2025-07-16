package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();


    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=c7463905";

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    private List<Serie> series = new ArrayList<>();

    private Optional<Serie> serieBusca;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu(){
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por título
                    5 - Buscar séries por ator 
                    6 - Top 5 Séries
                    7 - Buscar séries por categoria
                    8 - Buscar Series Por numero de temporadas e com avaliacao de determinado valor
                    9 - Buscar epsódio por trecho de nome
                    10 - Top 5 Episodios por serie buscada
                    11 - Buscar episodio apartir de uma data
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    buscarSeriesPorTemporadasEPorAvaliacao();
                    break;
                case 9:
                    buscarEpsodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;

                case 11:
                    buscarEpisodiosDepoisDeUmaData();
                    break;

                case 0:
                    System.out.println("Saindo...");
            }
        }
    }



    private void buscarSerieWeb() {
            DadosSerie dados = getDadosSerie();
            Serie serie = new Serie(dados);
            //  dadosSeries.add(dados);
            repositorio.save(serie);
            System.out.println(dados);
        }

        private DadosSerie getDadosSerie() {
            System.out.println("Digite o nome da série para busca");
            var nomeSerie = leitura.nextLine();
            var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
            return dados;
        }


        private void buscarEpisodioPorSerie(){
           listarSeriesBuscadas();
           System.out.println("Escolha uma série pelo nome: ");
           var nomeSerie = leitura.nextLine();

             Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

             if (serie.isPresent()) {

                 var serieEncontrada = serie.get();
                 List<DadosTemporada> temporadas = new ArrayList<>();

                 for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                     var json = consumo.obterDados((ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY));

                     DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);

                     temporadas.add(dadosTemporada);
                 }

                 temporadas.forEach(System.out::println);

                  List<Episodio> episodios = temporadas.stream()
                         .flatMap(d -> d.episodios().stream()
                                 .map(e -> new Episodio(d.numero(), e)))
                         .collect(Collectors.toList());

                  serieEncontrada.setEpisodios(episodios);
                  repositorio.save(serieEncontrada);
             }else {
                 System.out.println("Série não encontrada!");
             }
        }

        private  void listarSeriesBuscadas(){
            series = repositorio.findAll();
            series.stream()
                            .sorted(Comparator.comparing(Serie::getCategoria))
            .forEach(System.out::println);
        }


    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();

        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void buscarSeriesPorAtor() {

        System.out.println("Qual nome para busca? ");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de que valor? ");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " Avaliação " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> serieTop = repositorio.findTop5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s ->
                System.out.println(s.getTitulo() + " Avaliação " + s.getAvaliacao()));
    }


    private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar séries de que categoria/gênero? ");
        var nomeCategoria = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeCategoria);
        List<Serie> seriePorCategoria = repositorio.findByCategoria(categoria);
        System.out.println("Séries da categoria " + nomeCategoria);
        seriePorCategoria.forEach(System.out::println);
    }

    private void buscarSeriesPorTemporadasEPorAvaliacao() {

        System.out.println("Digite o numero de temporadas ");
        var quantidadeTemporadas = leitura.nextInt();
        System.out.println("Avaliações a partir de que valor? ");
        var avaliacao = leitura.nextDouble();

        List<Serie> serieTemporadaAvaliacao = repositorio.seriesPorTemporadaEAvaliacao(quantidadeTemporadas, avaliacao);
        System.out.println(" Series com até " + quantidadeTemporadas +  " temporadas" +
                " e Com avaliação a partir de " + avaliacao);
        serieTemporadaAvaliacao.forEach(System.out::println);

    }

    private void buscarEpsodioPorTrecho() {

        System.out.println("Qual nome do epsódio para busca? ");
        var trechoEpisodio = leitura.nextLine();

        List<Episodio> epsodiosEncontrados =  repositorio.episodiosPorTrecho(trechoEpisodio);
        epsodiosEncontrados.forEach(e ->
                System.out.printf(" Serie: %s Temporada %s - Episódio %s - %s\n ",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));
    }


    private void topEpisodiosPorSerie() {

        buscarSeriePorTitulo();

        if (serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodio = repositorio.topEpisodiosPorSerie(serie);
            topEpisodio.forEach(e ->
                    System.out.printf(" Serie: %s Temporada %s - Episódio %s - %s Avaliação %s\n ",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }

    private void buscarEpisodiosDepoisDeUmaData() {
        buscarSeriePorTitulo();

        if (serieBusca.isPresent()){
            System.out.println("Digite o ano limite de lançamento");
            Serie serie = serieBusca.get();
            var anoLancamento = leitura.nextInt();
            leitura.nextLine();

            List<Episodio> episodiosAno = repositorio.episodiosPorSerieEAno(serie, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }
}



















