package main;

import card.Card;
import card.CardAux;
import card.CardPanel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Frame extends JFrame implements ActionListener
{
    private final int distX = Main.CELL_WIDTH;
    private final int distY = Main.CELL_HEIGHT;

    JLayeredPane lp = new JLayeredPane();

    private final ArrayList<CardAux> auxTop = new ArrayList<>();
    private final ArrayList<CardAux> auxLeft = new ArrayList<>();
    
    List<List<CardAux>> containers = new ArrayList<>();
    private final int numPhases = 4;
    private int currentPhase = 0;

    private LinkedList<Card> cards;

    private int cardDepth = 0; // define a posição das cartas com relação a outras cartas quando está uma na mesma área da outra
    
    private final String[] typeCharMap = {"(I)", "(A)", "(S)", "(E)", "(C)", "(R)"};
    private final String[] typeColorMap = {"<font color=\"rgb(80,80,80)\">", "<font color=\"rgb(248,0,128)\">", "<font color=\"rgb(0,64,128)\">", "<font color=\"rgb(248,128,0)\">", "<font color=\"rgb(192,0,0)\">", "<font color=\"rgb(0,96,0)\">"};

    // posição onde vai ficar a carta atual. setada a cada phase
    private int xCardToDo;
    private int yCardToDo;
    
    // salva screenshots se for a primeira vez visitando as telas de hex e tabela final
    private Runnable screenshot;
    private Runnable setPhase;
    private Runnable addBtn;
    private int screenshotsTaken = 0;
    
    // buttons
    private final JButton swapBtn = new JButton();
    private final JButton undoBtn = new JButton("Desfazer");
    private final int btnWidth = (int)(Main.CELL_WIDTH*0.65);
    private final int btnHeight = (int)(Main.CELL_HEIGHT*0.4);
    
    // undo
    private final Stack<Card> undoStack = new Stack<>();
    private final Stack<CardAux> clickableStack = new Stack<>();
    
    private ResultsHexPanel rp;

    public Frame(LinkedList<Card> cards)
    {
        this.cards = cards;
        initAux();
        initComponents();
        initRunnables();
    }

    private void initAux()
    {
        auxTop.add(new CardAux("(1)", "Altamente habilidoso"));
        auxTop.add(new CardAux("(2)", "Habilidade média"));
        auxTop.add(new CardAux("(3)", "Pouco ou nada habilidoso"));

        auxLeft.add(new CardAux("(A)", "Adoro usar"));
        auxLeft.add(new CardAux("(B)", "Gosto muito de usar"));
        auxLeft.add(new CardAux("(C)", "Gosto de usar"));
        auxLeft.add(new CardAux("(D)", "Não gosto de usar"));
        auxLeft.add(new CardAux("(E)", "Detesto usar"));
        
        for(int i = 0; i < numPhases; i++)
        {
            containers.add(new ArrayList<>());
        }

        for (int i = 1; i <= auxTop.size(); i++)
        {
            containers.get(0).add(new CardAux(this)); // fase inicial
        }
        
        for (int i = 1; i <= auxLeft.size(); i++)
        {
            containers.get(1).add(new CardAux(this)); // coluna A
            containers.get(2).add(new CardAux(this)); // coluna B
            containers.get(3).add(new CardAux(this)); // coluna C
        }
    }

    private void initComponents()
    {
        setTitle("Matriz de Habilidades e Interesses Profissionais");
        setLocation(6, 6);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        
        undoBtn.setSize(btnWidth, btnHeight);
        swapBtn.setSize(btnWidth, btnHeight);
        
        undoBtn.addActionListener(this);
        swapBtn.addActionListener(this);

        add(lp);
        lp.setLocation(0, 0);

        setupPhase(currentPhase);
    }
    
    public void updateCardToDo()
    {
        undoBtn.setEnabled(!undoStack.isEmpty());
        cardDepth++;

        if (!cards.isEmpty()) // existem cartas na pilha
        {
            CardPanel panel = cards.getFirst().getPanel();
            lp.add(panel, new Integer(cardDepth));
            panel.setLocation(xCardToDo, yCardToDo);
            lp.moveToBack(panel);
        }
        else // phase change
        {
            cardDepth = 0;
            currentPhase++;
            setupPhase(currentPhase);

            if(currentPhase == 4 && screenshotsTaken == 0)
            {
                java.awt.EventQueue.invokeLater(screenshot);
                currentPhase++;
                java.awt.EventQueue.invokeLater(setPhase);
                java.awt.EventQueue.invokeLater(screenshot);
                java.awt.EventQueue.invokeLater(addBtn);
            }
        }
        repaint();
    }

    public Card dequeue()
    {
        return cards.pop();
    }

    public int cardsLeft()
    {
        return cards.size();
    }

    public int getPhase()
    {
        return currentPhase;
    }
    
    public Stack<Card> getUndoStack()
    {
        return undoStack;
    }
    
    public Stack<CardAux> getClickableStack()
    {
        return clickableStack;
    }
    
    private void cleanUndoStacks()
    {
        undoStack.clear();
        clickableStack.clear();
    }
    
    private void initRunnables()
    {
        screenshot = () ->
        {
            String type = screenshotsTaken == 0 ? "tab" : "hex";

            String filename = new SimpleDateFormat("dd-MM-yy_HHmm").format(new Date()) + "_" + type + ".png";
            File output = new File(filename);

            Rectangle rec = getRootPane().getBounds();
            BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_RGB);
            getRootPane().paint(bufferedImage.getGraphics());
            getRootPane().paint(bufferedImage.getGraphics());

            try
            {
                ImageIO.write(bufferedImage, "png", output);
                screenshotsTaken++;
            }
            catch (IOException ioe)
            {
                System.out.println("IOException: " + ioe);
            }
        };

        setPhase = () ->
        {
            setupPhase(currentPhase);
        };
        
        addBtn = () ->
        {
            rp.add(swapBtn);
            swapBtn.setLocation(Main.CELL_WIDTH/2 - btnWidth/2, Main.CELL_HEIGHT/2 - btnHeight/2);
        };
    }

    private void setupPhase(int phase)
    {
        lp.removeAll();

        if(phase == 0) // distribuir 72 cards em (1)(2)(3) [habilidade]
        {
            setSize(Main.WIDTH_P0 + getInsets().left + getInsets().right, Main.HEIGHT_P0 + getInsets().top + getInsets().bottom);
            lp.setSize(Main.WIDTH_P0, Main.HEIGHT_P0);

            // adiciona aux top
            for (int i = 0; i < auxTop.size(); i++)
            {
                lp.add(auxTop.get(i), new Integer(-1));
                auxTop.get(i).setLocation(distX*i, 0);
            }

            // adiciona containers
            for (int i = 0; i < containers.get(phase).size(); i++)
            {
                lp.add(containers.get(phase).get(i), new Integer(0));
                containers.get(phase).get(i).setLocation(distX*i, distY);
            }
            
            xCardToDo = 3 * Main.WIDTH_P0 / (containers.get(phase).size()*4);
            yCardToDo = distY*2 + Main.CARDEXTRA_Y/2 - Main.CARD_HEIGHT/2;
            
            // undo actions
            undoBtn.setLocation(7 * Main.WIDTH_P0/12, distY*2 + Main.CARDEXTRA_Y/2 - btnHeight/2);
            lp.add(undoBtn, new Integer(0));
            
            updateCardToDo();
        }

        if(phase >= 1 && phase <= 3) // distribuir cards em (3)(2)(1) em (A)(B)(C)(D)(E) [afinidade]
        {
            cards = new LinkedList<>(containers.get(0).get(3-phase).getCards());
            setSize(Main.WIDTH_P1 + getInsets().left + getInsets().right, Main.HEIGHT_P1 + getInsets().top + getInsets().bottom);
            lp.setSize(Main.WIDTH_P1, Main.HEIGHT_P1);

            // adiciona aux left
            for (int i = 0; i < auxLeft.size(); i++)
            {
                lp.add(auxLeft.get(i), new Integer(-1));
                auxLeft.get(i).setLocation(0, distY*i);
            }

            // adiciona containers
            for (int i = 0; i < containers.get(4-phase).size(); i++)
            {
                lp.add(containers.get(4-phase).get(i), new Integer(0));
                containers.get(4-phase).get(i).setLocation(distX, distY*i);
            }
            
            // adiciona label
            lp.add(auxTop.get(3-phase), new Integer(-1));
            auxTop.get(3-phase).setLocation(distX*2 + Main.CARDEXTRA_X/2 - Main.CELL_WIDTH/2, distY);
            
            // posiciona a próxima carta a ser colocada
            xCardToDo = distX*2 + Main.CARDEXTRA_X/2 - Main.CARD_WIDTH/2;
            yCardToDo = 9 * Main.HEIGHT_P1 / (containers.get(4-phase).size()*4);
            
            // undo actions
            cleanUndoStacks();
            undoBtn.setLocation(distX*2 + Main.CARDEXTRA_X/2 - btnWidth/2, 13 * Main.HEIGHT_P1 / (containers.get(4-phase).size()*4));
            lp.add(undoBtn, new Integer(0));
            
            updateCardToDo();
        }

        if(phase == Main.RESULTSPHASE_TABLE) // results table
        {
            setSize(Main.WIDTH_FULL + getInsets().left + getInsets().right, Main.HEIGHT_FULL + getInsets().top + getInsets().bottom);            
            lp.setSize(Main.WIDTH_FULL, Main.HEIGHT_FULL);
            
            swapBtn.setText("Mostrar Hexágono");

            // adiciona aux top/left
            for (int i = 0; i < auxTop.size(); i++)
            {
                lp.add(auxTop.get(i), new Integer(-1));
                auxTop.get(i).setLocation(distX * (i + 1), 0);
            }
            for (int i = 0; i < auxLeft.size(); i++)
            {
                lp.add(auxLeft.get(i), new Integer(-1));
                auxLeft.get(i).setLocation(0, distY * (i + 1));
            }

            // adiciona containers (não-clickables nesta fase)
            for (int i = 1; i <= auxTop.size(); i++)
            {
                for(int j = 0; j < containers.get(i).size(); j++)
                {
                    lp.add(containers.get(i).get(j), new Integer(0));
                    containers.get(i).get(j).setLocation(distX * i, distY * (j + 1));

                    String cardListTxt = new String();
                    for(int k = 0; k < containers.get(i).get(j).getCards().size(); k++)
                    {
                        Card tmp = containers.get(i).get(j).getCards().get(k);
                        cardListTxt = cardListTxt + typeColorMap[tmp.getTipo()] + tmp.getTitulo() + " " + typeCharMap[tmp.getTipo()] + "</font>, ";
                    }
                    
                    cardListTxt = cardListTxt.replaceAll(", $", ""); // remove , no final da string
                    cardListTxt = "<html><center>" + cardListTxt + "</center></html>"; // formatação

                    JLabel txtLabel = new JLabel();
                    txtLabel.setFont(new java.awt.Font("SansSerif", 1, 12));
                    txtLabel.setSize(Main.CELL_WIDTH, Main.CELL_HEIGHT);
                    txtLabel.setLocation(0, 0);
                    txtLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    txtLabel.setText(cardListTxt);
                    
                    containers.get(i).get(j).add(txtLabel);
                }

                if(screenshotsTaken >= 2)
                {
                    lp.add(swapBtn, new Integer(0));
                    swapBtn.setLocation(Main.CELL_WIDTH/2 - btnWidth/2, Main.CELL_HEIGHT/2 - btnHeight/2);
                }
            }
            repaint();
        }
            
        if(phase == Main.RESULTSPHASE_HEX) // results hex
        {
            setSize(Main.WIDTH_FULL + getInsets().left + getInsets().right, Main.HEIGHT_FULL + getInsets().top + getInsets().bottom);
            
            swapBtn.setText("Mostrar Tabela");

            // passar os resultados dos cliques relevantes aqui (A1 e B1)
            rp = new ResultsHexPanel(containers.get(1).get(0).getCont(), containers.get(1).get(1).getCont());

            rp.setBounds(0, 0, Main.WIDTH_FULL, Main.HEIGHT_FULL);
            lp.add(rp, new Integer(0));

            if (screenshotsTaken >= 2)
            {
                rp.add(swapBtn);
                swapBtn.setLocation(Main.CELL_WIDTH/2 - btnWidth/2, Main.CELL_HEIGHT/2 - btnHeight/2);
            }
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == undoBtn)
        {
            Card c = undoStack.pop();
            
            CardAux clickable = clickableStack.pop();
            clickable.getCards().remove(c);
            clickable.getCont()[c.getTipo()]--;
            
            lp.remove(c.getPanel());
            cards.addFirst(c);

            updateCardToDo();
        }
        if(e.getSource() == swapBtn)
        {
            if(currentPhase == Main.RESULTSPHASE_TABLE)
            {
                currentPhase = Main.RESULTSPHASE_HEX;
                setupPhase(currentPhase);
            }
            else if(currentPhase == Main.RESULTSPHASE_HEX)
            {
                currentPhase = Main.RESULTSPHASE_TABLE;
                setupPhase(currentPhase);
            }
        }
    }
}
