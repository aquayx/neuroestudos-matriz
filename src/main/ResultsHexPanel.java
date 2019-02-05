package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultsHexPanel extends JPanel
{
    private final int hexSize = 120;
    private final int hexWidth = (int)((2 * Math.sin(Math.toRadians(30)) * hexSize) + hexSize); // (240 if hexSize=120, 400 if hexSize=200)
    private final int hexHeight = (int)(2 * Math.cos(Math.toRadians(30)) * hexSize);            // (207 if hexSize=120, 346 if hexSize=200)
    
    private final int letraSize = 30; // tamanho base da fonte

    private final int ox = Main.WIDTH_FULL/2 - hexWidth/2; // hex offset x (520 if hexSize=120, 448 if hexSize=200)
    private final int oy = 10 + (int)(letraSize*2.5);      // hex offset y (85 if letraSize=30)

    // contorno
    private final int[] hexX = new int[]{hexWidth/4*3+ox, hexWidth+ox, hexWidth/4*3+ox, hexWidth/4+ox, 0+ox, hexWidth/4+ox};
    private final int[] hexY = new int[]{0+oy, hexHeight/2+oy, hexHeight+oy, hexHeight+oy, hexHeight/2+oy, 0+oy};
    private final Polygon hex = new Polygon(hexX, hexY, 6);

    // polígonos coloridos
    private final HashMap<Polygon, Color> polygonColor = new HashMap<>();
    private final ArrayList<Polygon> colorPolygons = new ArrayList<>();
    
    // textos
    private final ArrayList<JLabel> textos = new ArrayList<>();
    private final ArrayList<JLabel> letras = new ArrayList<>();
    private final JLabel labelHolland = new JLabel("<html><center>Hexágono de Holland</center></html>");

    // hexágonos de habilidade
    private ArrayList<Polygon> abilityPoly1;
    private ArrayList<Polygon> abilityPoly2;
    
    // armazena o número de itens relevantes a serem considerados em cada categoria para fácil acesso (recebe do frame)
    private final int[] rel = new int[6];
    private int maxRel = 0; // usado para abilityHex2

    public ResultsHexPanel(int[] a1, int[] a2)
    {
        for(int i = 0; i < rel.length; i++)
        {
            rel[i] = a1[i] + a2[i];
            if(rel[i] > maxRel)
            {
                maxRel = rel[i];
            }
        }
        initPolygons();
        initComponents();
    }
    
    private void initPolygons()
    {
        int[] p0X = new int[]{hexWidth/2+ox, hexWidth/2+ox, hexWidth/4*3+ox, hexWidth/8*7+ox};
        int[] p0Y = new int[]{hexHeight/2+oy, 0+oy, 0+oy, hexHeight/4+oy};
        Polygon p0 = new Polygon(p0X, p0Y, 4);
        colorPolygons.add(p0);
        polygonColor.put(p0, Main.GRAY);
        
        final int[] p1X = new int[]{hexWidth/2+ox, hexWidth/8*7+ox, hexWidth+ox, hexWidth/8*7+ox};
        final int[] p1Y = new int[]{hexHeight/2+oy, hexHeight/4+oy, hexHeight/2+oy, hexHeight/4*3+oy};
        final Polygon p1 = new Polygon(p1X, p1Y, 4);
        colorPolygons.add(p1);
        polygonColor.put(p1, Main.MAGENTA);
        
        final int[] p2X = new int[]{hexWidth/2+ox, hexWidth/8*7+ox, hexWidth/4*3+ox, hexWidth/2+ox};
        final int[] p2Y = new int[]{hexHeight/2+oy, hexHeight/4*3+oy, hexHeight+oy, hexHeight+oy};
        final Polygon p2 = new Polygon(p2X, p2Y, 4);
        colorPolygons.add(p2);
        polygonColor.put(p2, Main.BLUE);

        final int[] p3X = new int[]{hexWidth/2+ox, hexWidth/2+ox, hexWidth/4+ox, hexWidth/8+ox};
        final int[] p3Y = new int[]{hexHeight/2+oy, hexHeight+oy, hexHeight+oy, hexHeight/4*3+oy};
        final Polygon p3 = new Polygon(p3X, p3Y, 4);
        colorPolygons.add(p3);
        polygonColor.put(p3, Main.ORANGE);

        final int[] p4X = new int[]{hexWidth/2+ox, hexWidth/8+ox, 0+ox, hexWidth/8+ox};
        final int[] p4Y = new int[]{hexHeight/2+oy, hexHeight/4+oy, hexHeight/2+oy, hexHeight/4*3+oy};
        final Polygon p4 = new Polygon(p4X, p4Y, 4);
        colorPolygons.add(p4);
        polygonColor.put(p4, Main.RED);

        final int[] p5X = new int[]{hexWidth/2+ox, hexWidth/8+ox, hexWidth/4+ox, hexWidth/2+ox};
        final int[] p5Y = new int[]{hexHeight/2+oy, hexHeight/4+oy, 0+oy, 0+oy};
        final Polygon p5 = new Polygon(p5X, p5Y, 4);
        colorPolygons.add(p5);
        polygonColor.put(p5, Main.GREEN);
    }

    private void initComponents()
    {
        setSize(Main.WIDTH_FULL, Main.HEIGHT_FULL);
        setLayout(null);
        
        textos.add(new JLabel("<html><b>Investigativo [" + rel[0] + "]:</b> Prefere resolver problemas abstratos, envolvendo ciências ou assuntos de engenharia. Curioso acerca do mundo físico e por que e como as coisas funcionam. Gosta de desafios intelectuais e atitudes originais e não convencionais.</html>"));
        textos.add(new JLabel("<html><b>Artístico [" + rel[1] + "]:</b> Prefere situações não estruturadas envolvendo auto-expressão de ideias e conceitos através de diferentes meios artísticos, tais como arte, música, teatro, filmes, multimídia ou escrita.</html>"));
        textos.add(new JLabel("<html><b>Social [" + rel[2] + "]:</b> Prefere oportunidades de suporte e ajuda envolvendo aconselhamento, orientação, ensino ou discussões em grupo. É inclinado a causas sociais e humanísticas.</html>"));
        textos.add(new JLabel("<html><b>Empreendedor [" + rel[3] + "]:</b> Prefere situações de negócios envolvendo persuasão, vendas, influência, entusiasmo. É enérgico, assertivo e auto-confiante. É inclinado ao gerenciamento, liderança e marketing.</html>"));
        textos.add(new JLabel("<html><b>Convencional [" + rel[4] + "]:</b> Prefere situações de negócios estruturadas, envolvendo análise de dados, finanças e tarefas organizacionais. Valoriza ordem e eficiência.</html>"));
        textos.add(new JLabel("<html><b>Realista [" + rel[5] + "]:</b> Prefere atividades práticas, mão na massa, físicas, com resultados tangíveis. Prefere construir, reparar objetos, coisas mecânicas, ou trabalhar ao ar livre.</html>"));

        textos.get(0).setForeground(Main.GRAY);
        textos.get(1).setForeground(Main.MAGENTA);
        textos.get(2).setForeground(Main.BLUE);
        textos.get(3).setForeground(Main.ORANGE);
        textos.get(4).setForeground(Main.RED);
        textos.get(5).setForeground(Main.GREEN);
        
        for(int i = 0; i < textos.size(); i++)
        {
            int labelHeight = 60;
            textos.get(i).setSize(Main.WIDTH_FULL/10*8, labelHeight);
            textos.get(i).setFont(new java.awt.Font("SansSerif", 0, (int)(letraSize*0.6)));
            add(textos.get(i));
            textos.get(i).setLocation(Main.WIDTH_FULL/10, hexSize*2 + (int)(labelHeight*1.5) + labelHeight*i); // 490
        }
        
        letras.add(new JLabel("I"));
        letras.add(new JLabel("A"));
        letras.add(new JLabel("S"));
        letras.add(new JLabel("E"));
        letras.add(new JLabel("C"));
        letras.add(new JLabel("R"));
        
        for(int i = 0; i < letras.size(); i++)
        {
            letras.get(i).setSize(letraSize, letraSize);
            letras.get(i).setFont(new java.awt.Font("Monospaced", 1, letraSize));
            add(letras.get(i));
        }
        letras.get(0).setLocation(hexWidth/4*3+ox - letraSize/4, 0+oy - letraSize);
        letras.get(1).setLocation(hexWidth+ox + letraSize/4, hexHeight/2+oy - letraSize/2);
        letras.get(2).setLocation(hexWidth/4*3+ox - letraSize/4, hexHeight+oy);
        letras.get(3).setLocation(hexWidth/4+ox - letraSize/4, hexHeight+oy);
        letras.get(4).setLocation(0+ox - letraSize, hexHeight/2+oy - letraSize/2);
        letras.get(5).setLocation(hexWidth/4+ox - letraSize/4, 0+oy - letraSize);

        add(labelHolland);
        labelHolland.setFont(new java.awt.Font("Monospaced", 1, letraSize+10));
        labelHolland.setSize(Main.WIDTH_FULL/4*3, (int)(letraSize*1.5));
        labelHolland.setLocation(Main.WIDTH_FULL/100*35, 10);

        abilityPoly1 = genAbilityHex(Main.CARDS_TIPO);
        abilityPoly2 = genAbilityHex(maxRel);
    }

    // calcula os pontos de um hexágono de habilidades dada uma certa proporção. crazy
    private ArrayList<Polygon> genAbilityHex(double divF)
    {
        ArrayList<Polygon> abilityHexPolygons = new ArrayList<>();
        for(int i = 0; i < colorPolygons.size(); i++)
        {
            int[] tempX = new int[]{applyDivF(colorPolygons.get(i).xpoints[0]-ox,divF,i)+ox, applyDivF(colorPolygons.get(i).xpoints[1]-ox,divF,i)+ox, applyDivF(colorPolygons.get(i).xpoints[2]-ox,divF,i)+ox, applyDivF(colorPolygons.get(i).xpoints[3]-ox,divF,i)+ox};
            int[] tempY = new int[]{applyDivF(colorPolygons.get(i).ypoints[0]-oy,divF,i)+oy, applyDivF(colorPolygons.get(i).ypoints[1]-oy,divF,i)+oy, applyDivF(colorPolygons.get(i).ypoints[2]-oy,divF,i)+oy, applyDivF(colorPolygons.get(i).ypoints[3]-oy,divF,i)+oy};
            int cFx = (int)(hexWidth/divF/2 * (divF-rel[i]));
            int cFy = (int)(hexHeight/divF/2 * (divF-rel[i]));
            int[] pX = new int[]{tempX[0]+cFx, tempX[1]+cFx, tempX[2]+cFx, tempX[3]+cFx};
            int[] pY = new int[]{tempY[0]+cFy, tempY[1]+cFy, tempY[2]+cFy, tempY[3]+cFy};
            abilityHexPolygons.add(new Polygon(pX, pY, 4));
        }

        return abilityHexPolygons;
    }
    
    private int applyDivF(int num, double divF, int pos) // pos = posição da habilidade em rel[]
    {
        return (int)(num/divF*rel[pos]);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2);
        
        // polígonos coloridos
        for(int i = 0; i < colorPolygons.size(); i++)
        {
            g2.setColor(polygonColor.get(colorPolygons.get(i)));
            g2.fillPolygon(colorPolygons.get(i));
        }
        
        // desenha contorno
        g2.setColor(Main.BLACK);
        g2.setStroke(new BasicStroke(4));
        g2.drawPolygon(hex);

        // polígonos de habilidade
        g2.setColor(Main.ALPHA_WHITE);
        for(int i = 0; i < colorPolygons.size(); i++)
        {
            g2.fillPolygon(abilityPoly1.get(i));
            g2.fillPolygon(abilityPoly2.get(i));
            g2.setStroke(new BasicStroke(2));
            g2.drawPolygon(abilityPoly2.get(i));
        }
    }
}
