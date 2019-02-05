package main;

import card.Card;
import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;

public class Main
{
    // variáveis globais

    // tamanhos de componentes
    // célula maior e containers
    public static final int CELL_WIDTH = 320;
    public static final int CELL_HEIGHT = 120;
    // cards
    public static final int CARD_WIDTH = (int)(CELL_WIDTH*0.875);
    public static final int CARD_HEIGHT = (int)(CELL_HEIGHT*0.75);
    // tamanhos de janela
    // espaço extra para adicionar o cardToDo
    public static final int CARDEXTRA_X = (int)(CARD_WIDTH*1.8);
    public static final int CARDEXTRA_Y = (int)(CARD_HEIGHT*1.8);
    // janelas
    public static final int WIDTH_P0 = CELL_WIDTH*3;
    public static final int HEIGHT_P0 = CELL_HEIGHT*2 + CARDEXTRA_Y;
    public static final int WIDTH_P1 = CELL_WIDTH*2 + CARDEXTRA_X;
    public static final int HEIGHT_P1 = CELL_HEIGHT*5;
    public static final int WIDTH_FULL = CELL_WIDTH*4;   // 1280
    public static final int HEIGHT_FULL = CELL_HEIGHT*6; // 720

    // número de cards de cada tipo (setado na main)
    public static int CARDS_TIPO;
    
    // fases do programa
    public static int RESULTSPHASE_TABLE = 4;
    public static int RESULTSPHASE_HEX = 5;

    // cores
    public static final Color NE_ORANGE = new Color(200, 100, 0);
    public static final Color WHITE = new Color(248, 248, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color GRAY = new Color(80, 80, 80);
    public static final Color MAGENTA = new Color(248, 0, 128);
    public static final Color BLUE = new Color(0, 64, 128);
    public static final Color ORANGE = new Color(248, 128, 0);
    public static final Color RED = new Color(192, 0, 0);
    public static final Color GREEN = new Color(0, 96, 0);
    public static final Color ALPHA_WHITE = new Color(248, 248, 255, 176);
    public static final Color ALPHA_OUTLINE = new Color(128, 128, 128, 128);

    public static void main(String[] args)
    {
        LinkedList<Card> cards = new LinkedList<>();

        // adiciona todos os cards
        // 0: investigativo (gray)
        // 1: artístico (magenta)
        // 2: social (blue)
        // 3: empreendedor (orange)
        // 4: convencional (red)
        // 5: realista (green)
        
        // 0: investigativo
        cards.add(new Card(0, "INVESTIGAR", htmlAdd("Pesquisar, utilizando a observação, leituras ou entrevistas, para descobrir ou testar hipóteses científicas.")));
        cards.add(new Card(0, "CONCEITUAR", htmlAdd("Usar modelos teóricos para entender fenômenos humanos ou naturais.")));
        cards.add(new Card(0, "INOVAR", htmlAdd("Inovar procedimentos estabelecidos, desenvolver alternativas.")));
        cards.add(new Card(0, "RESOLVER PROBLEMAS LÓGICOS", htmlAdd("Identificar as causas de um problema prático ou teórico e propor uma solução.")));
        cards.add(new Card(0, "VISUALIZAR", htmlAdd("Habilidade de perceber e antecipar futuras tendências e possibilidades.")));
        cards.add(new Card(0, "ESCREVER", htmlAdd("Demonstrar habilidades no uso da linguagem escrita para a explanação de conceitos complexos.")));
        cards.add(new Card(0, "PRODUZIR IDEIAS", htmlAdd("Refletir, conceber novas e criativas ideias, teorias ou processos.")));
        cards.add(new Card(0, "OBSERVAR", htmlAdd("Estudar, escrutinar, examinar com atenção dados, pessoas ou coisas.")));
        cards.add(new Card(0, "INVENTAR", htmlAdd("Criar um novo produto, conceito ou processo através do estudo teórico e/ou empírico.")));
        cards.add(new Card(0, "LER/ESTUDAR", htmlAdd("Pesquisar e explorar fontes escritas exaustivamente e aprofundar conhecimentos.")));
        cards.add(new Card(0, "SINTETIZAR", htmlAdd("Articular fatos, conceitos ou ideias revelando seus padrões significativos de interação.")));
        cards.add(new Card(0, "CALCULAR", htmlAdd("Usar matemática para compreender e resolver problemas complexos.")));

        // 1: artístico
        cards.add(new Card(1, "ENTRETER", htmlAdd("Apresentar obra artística, entreter o público com recursos cênicos e/ou de oratória.")));
        cards.add(new Card(1, "DESIGN", htmlAdd("Conceber formas estéticas e funcionais para peças de todo tipo (vestuário, móveis, objetos, etc.).")));
        cards.add(new Card(1, "IMAGINAR", htmlAdd("Fantasiar, inventar, conceber possibilidades, estar aberto ao novo.")));
        cards.add(new Card(1, "REDAÇÃO CRIATIVA", htmlAdd("Expressar-se criativamente através de produções literárias ou jornalísticas (livros, contos, poesias).")));
        cards.add(new Card(1, "UTILIZAR A INTUIÇÃO", htmlAdd("Contar com pressentimentos e outros estados subjetivos peculiares como base para a percepção ou tomada de decisões.")));
        cards.add(new Card(1, "RETRATAR", htmlAdd("Expressar ideias ou estados subjetivos através de desenhos, pinturas, fotografias ou esculturas.")));
        cards.add(new Card(1, "COMPOSIÇÃO MUSICAL", htmlAdd("Compor ou arranjar peças musicais.")));
        cards.add(new Card(1, "DECORAR AMBIENTES", htmlAdd("Escolher e dispor objetos de modo a ornar, enfeitar, embelezar interiores.")));
        cards.add(new Card(1, "ATUAR/DRAMATIZAR", htmlAdd("Interpretar papéis, dar vida a personagens, ideias ou problemas em diversos contextos (teatro, TV, propaganda, apresentações, etc.).")));
        cards.add(new Card(1, "PRODUZIR FIGURINOS", htmlAdd("Produzir e/ou escolher vestuário para personagens em produções cênicas (teatro, TV, cinema) ou para pessoas públicas.")));
        cards.add(new Card(1, "EXIBIR/EXPOR", htmlAdd("Usar senso estético para apresentar ou representar ideias ou objetos em público.")));
        cards.add(new Card(1, "PRODUZIR ARTESANATOS", htmlAdd("Usar inventividade, motricidade fina e senso estético para produzir objetos belos e incomuns.")));

        // 2: social
        cards.add(new Card(2, "ENTREVISTAR", htmlAdd("Obter opinições e informações através de conversa e questionamento.")));
        cards.add(new Card(2, "TREINAR/INSTRUIR", htmlAdd("Ensinar ou capacitar através de explicações e demonstrações práticas.")));
        cards.add(new Card(2, "CONECTAR PESSOAS", htmlAdd("Facilitar vínculos entre pessoas ou grupos, aperfeiçoar a comunicação, promover cooperação.")));
        cards.add(new Card(2, "EXPLICAR", htmlAdd("Comunicar informações ou ideias de forma organizada, clara e paciente.")));
        cards.add(new Card(2, "RECEBER/HOSPEDAR", htmlAdd("Receber e oferecer conforto a pessoas.")));
        cards.add(new Card(2, "FALAR PARA GRUPOS", htmlAdd("Expor ideias e informações a fim de esclarecer, orientar e ajudar.")));
        cards.add(new Card(2, "CONTATO COM O PÚBLICO", htmlAdd("Receber e saudar pessoas, dar informações, orientar.")));
        cards.add(new Card(2, "LIDAR COM SENTIMENTOS", htmlAdd("Facilitar a expressão, ouvir e mostrar sensibilidade e respeito a sentimentos, aplacar tensões, animar.")));
        cards.add(new Card(2, "ACONSELHAR", htmlAdd("Ouvir com empatia, facilitar o esclarecimento de estados subjetivos, e auxiliar na tomada de decisões.")));
        cards.add(new Card(2, "DIPLOMACIA", htmlAdd("Facilitar a interação entre partes em conflito e chegar a um acordo amigável.")));
        cards.add(new Card(2, "TRATAR/CUIDAR", htmlAdd("Promover o ajustamento, saúde ou o bem-estar de outras pessoas.")));
        cards.add(new Card(2, "ESCUTAR", htmlAdd("Concentrar-se no discurso do interlocutor e compreender o que este comunica.")));

        // 3: empreendedor
        cards.add(new Card(3, "PLANEJAR", htmlAdd("Estabelecer etapas e ações encadeadas para alcançar resultados, desenvolver projetos.")));
        cards.add(new Card(3, "LIDERAR", htmlAdd("Iniciar processos de mudança, inspirar e conduzir outras pessoas na busca de novas possibilidades.")));
        cards.add(new Card(3, "INFLUENCIAR/PERSUADIR", htmlAdd("Convencer outras pessoas a mudar pensamentos ou atitudes ou a agir de modo específico.")));
        cards.add(new Card(3, "VENDER", htmlAdd("Mostrar o valor de um serviço ou produto para atender necessidades de pessoas ou grupos, convencer a comprar.")));
        cards.add(new Card(3, "DELEGAR", htmlAdd("Designar tarefas para outras pessoas de acordo com suas capacidades para cumpri-las a contento.")));
        cards.add(new Card(3, "NEGOCIAR", htmlAdd("Barganhar e permutar vantagens para alcançar um acordo satisfatório.")));
        cards.add(new Card(3, "DESPACHAR", htmlAdd("Desembaraçar processos de produção ou serviços, identificando e eliminando entraves.")));
        cards.add(new Card(3, "PROMOVER/DIVULGAR", htmlAdd("Fazer conhecer ou chamar a atenção sobre uma pessoa, produto ou serviço através de meios de comunicação e/ou situações sociais.")));
        cards.add(new Card(3, "COORDENAR", htmlAdd("Orquestrar a execução integrada de atividades de produção ou serviços.")));
        cards.add(new Card(3, "IMPLEMENTAR", htmlAdd("Tomar as providências práticas necessárias para cumprir um planejamento.")));
        cards.add(new Card(3, "MOTIVAR", htmlAdd("Estimular indivíduos ou grupos a perseguir objetivos com entusiasmo.")));
        cards.add(new Card(3, "SUPERVISIONAR", htmlAdd("Observar e orientar o desempenho de outras pessoas.")));
        
        // 4: convencional
        cards.add(new Card(4, "ORGANIZAR", htmlAdd("Ordenar elementos ou atividades para obter uma situação estruturada e funcional.")));
        cards.add(new Card(4, "CATEGORIZAR", htmlAdd("Organizar dados ou objetos em grupos de acordo com critérios específicos.")));
        cards.add(new Card(4, "ANALISAR", htmlAdd("Examinar e discernir aspectos ou partes de eventos, dados, ideias ou objetos.")));
        cards.add(new Card(4, "REVISAR/EDITAR", htmlAdd("Revisar, corrigir e melhorar textos escritos.")));
        cards.add(new Card(4, "CONTROLAR", htmlAdd("Acompanhar e/ou corrigir a evolução de dados, pessoas ou coisas.")));
        cards.add(new Card(4, "TESTAR", htmlAdd("Medir qualidade ou validade de produtos através de metodologia objetiva.")));
        cards.add(new Card(4, "ADMINISTRAR DADOS", htmlAdd("Coletar, sistematizar e manter informações organizadas e disponíveis para acesso.")));
        cards.add(new Card(4, "FISCALIZAR", htmlAdd("Verificar o cumprimento de regras e procedimentos padronizados, ou a correspondência entre registros e fatos.")));
        cards.add(new Card(4, "ORÇAR", htmlAdd("Estimar, planejar e agendar gastos ou custos em um determinado período.")));
        cards.add(new Card(4, "MONITORAR", htmlAdd("Observar e regular procedimentos técnicos de acordo com critérios normativos.")));
        cards.add(new Card(4, "ESTRUTURAR ROTINAS", htmlAdd("Definir procedimentos padronizados de verificação, registro ou controle de dados, pessoas ou coisas.")));
        cards.add(new Card(4, "LIDAR COM FINANÇAS", htmlAdd("Utilizar adequadamente dinheiro ou outros recursos, poupar, investir.")));

        // 5: realista
        cards.add(new Card(5, "REPARAR/CONSERTAR", htmlAdd("Diagnosticar e corrigir problemas no funcionamento de equipamentos mecânicos, eletrônicos, ou organismos vivos.")));
        cards.add(new Card(5, "TRANSPORTAR", htmlAdd("Dirigir veículos transportando passageiros ou produtos.")));
        cards.add(new Card(5, "MANIPULAR COM PRECISÃO", htmlAdd("Usar destreza e precisão na manipulação de objetos, ferramentas e máquinas.")));
        cards.add(new Card(5, "USAR FORÇA FÍSICA", htmlAdd("Usar força e resistência em atividades físicas, suportar desgaste físico.")));
        cards.add(new Card(5, "USAR HABILIDADES MECÂNICAS", htmlAdd("Montar, ajustar, consertar, usando ferramentas e/ou o próprio corpo.")));
        cards.add(new Card(5, "CULTIVAR PLANTAS", htmlAdd("Cultivar vegetais para fins ornamentais ou de alimentação. Preparar o solo, semear, fertilizar, eliminar pragas, colher, etc.")));
        cards.add(new Card(5, "LIDAR COM ANIMAIS", htmlAdd("Alimentar, reproduzir, treinar ou expor animais domésticos ou do campo.")));
        cards.add(new Card(5, "UTILIZAR COORDENAÇÃO CORPORAL", htmlAdd("Usar o corpo com destreza, equilíbrio e precisão de movimentos.")));
        cards.add(new Card(5, "EXECUTAR PROCEDIMENTOS TÉCNICOS", htmlAdd("Usar ferramentas eletrônicas ou mecânicas para obter resultados específicos em intervenções técnicas.")));
        cards.add(new Card(5, "CONSTRUIR", htmlAdd("Erguer estruturas de todo tipo, executar projetos de construção.")));
        cards.add(new Card(5, "OPERAR EQUIPAMENTOS", htmlAdd("Operar equipamentos eletrônicos ou mecânicos.")));
        cards.add(new Card(5, "LIDAR COM CARPINTARIA", htmlAdd("Construir, reformar ou fazer a manutenção de estruturas e objetos.")));

        // embaralha antes de começar
        Collections.shuffle(cards);

        CARDS_TIPO = cards.size()/6;
        Frame f = new Frame(cards);
    }

    private static String htmlAdd(String s)
    {
        return("<html><center>" + s + "</center></html>");
    }
}
