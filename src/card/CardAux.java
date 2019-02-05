package card;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.Frame;
import main.Main;

public class CardAux extends JPanel implements MouseListener
{
    LinkedList<Card> cards = new LinkedList<>(); // armazena todos os cards colocados neste CardAux
    private final int type;

    private final int width = Main.CELL_WIDTH;
    private final int height = Main.CELL_HEIGHT;

    private final int cardWidth = Main.CARD_WIDTH;
    private final int cardHeight = Main.CARD_HEIGHT;

    private final Color white = new Color(248, 248, 255);
    private final Color black = new Color(0, 0, 0);
    private final Color gray = new Color(222, 222, 222);

    private final JLabel titulo;
    private final JLabel desc;

    private final Frame f;

    private final int[] cont; // contador de cards de cada tipo. poderia contar separadamente no final, mas assim economiza processamento

    public CardAux(String strTitulo, String strDesc) // left, top
    {
        type = 0;
        f = null;
        cont = new int[]{0, 0, 0, 0, 0, 0};

        titulo = new JLabel();
        desc = new JLabel();

        setLooks(strTitulo, strDesc);
        initComponents();
    }

    public CardAux(Frame f) // clickables
    {
        type = 1;
        this.f = f;
        cont = new int[]{0, 0, 0, 0, 0, 0};

        titulo = null;
        desc = null;

        setLooks();
        initComponents();
    }
    
    private void initComponents()
    {
        setSize(width, height);
        setLayout(null);
        
        if(type == 0)
        {
            add(titulo);
            titulo.setSize(width, height/3);
            titulo.setLocation(0, 0);
            add(desc);
            desc.setSize(width, getHeight()-titulo.getHeight());
            desc.setLocation(0, titulo.getHeight());
        }
        if(type == 1)
        {
            addMouseListener(this);
        }
    }
    
    private void setLooks(String strTitulo, String strDesc) // left, top
    {
        setBackground(white);
        titulo.setForeground(black);
        desc.setForeground(black);
        setBorder(BorderFactory.createLineBorder(black));

        titulo.setFont(new java.awt.Font("SansSerif", 1, 20));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText(strTitulo);

        desc.setFont(new java.awt.Font("SansSerif", 1, 16));
        desc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        desc.setText(strDesc);
    }
    
    private void setLooks() // clickables
    {
        setBackground(gray);
        setBorder(BorderFactory.createLoweredBevelBorder());
    }
    
    public int[] getCont()
    {
        return cont;
    }
    
    public LinkedList<Card> getCards()
    {
        return cards;
    }

    // métodos MouseListener
    @Override public void mousePressed(MouseEvent e)
    {
        if (type == 1 && f.getPhase() != Main.RESULTSPHASE_TABLE) // não ser clickable se já está mostrando os resultados
        {
            int cx = e.getX() - cardWidth/2;
            int cy = e.getY() - cardHeight/2;
            
            // mantém o card dentro da área permitida
            if(cx < 0) cx = 0;
            if(cx > width-cardWidth) cx = width-cardWidth;
            if(cy < 0) cy = 0;
            if(cy > height-cardHeight) cy = height-cardHeight;

            Card c = f.dequeue();
            cards.add(c);
            cont[c.getTipo()]++;
            f.getUndoStack().add(c);
            f.getClickableStack().add(this);
            
            c.getPanel().setLocation(getX()+cx, getY()+cy);
            f.updateCardToDo();
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
