package card;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import main.Main;

public class CardPanel extends JPanel
{
    private final Card card;
    
    // dimensões um pouco menores que o container (CardAux)
    private final int width = Main.CARD_WIDTH;
    private final int height = Main.CARD_HEIGHT;

    private final JLabel titulo;
    private final JLabel desc;

    public CardPanel(Card card)
    {
        this.card = card;
        titulo = new JLabel();
        desc = new JLabel();
        
        setLooks();
        
        titulo.setText(card.getTitulo());
        desc.setText(card.getDesc());
        
        initComponents();
    }

    private void initComponents()
    {
        setSize(width, height);
        
        setLayout(null);
        add(titulo);
        titulo.setSize(width, height/3);
        titulo.setLocation(0, 0);
        add(desc);
        desc.setSize(width, getHeight()-titulo.getHeight());
        desc.setLocation(0, titulo.getHeight());
    }

    private void setLooks()
    {
        final boolean monoColor = true; // define se as cartas serão coloridas ou não

        setBorder(BorderFactory.createEtchedBorder());

        titulo.setFont(new java.awt.Font("SansSerif", 1, 14));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        desc.setFont(new java.awt.Font("SansSerif", 0, 12));

        if(monoColor)
        {
            setBackground(Main.WHITE); titulo.setForeground(Main.NE_ORANGE); desc.setForeground(Main.NE_ORANGE);
        }
        else
        {
            switch(card.getTipo())
            {
                case 0: setBackground(Main.GRAY); titulo.setForeground(Main.WHITE); desc.setForeground(Main.WHITE); break;    // investigativo
                case 1: setBackground(Main.MAGENTA); titulo.setForeground(Main.WHITE); desc.setForeground(Main.WHITE); break; // artístico
                case 2: setBackground(Main.BLUE); titulo.setForeground(Main.WHITE); desc.setForeground(Main.WHITE); break;    // social
                case 3: setBackground(Main.ORANGE); titulo.setForeground(Main.WHITE); desc.setForeground(Main.WHITE); break;  // empreendedor
                case 4: setBackground(Main.RED); titulo.setForeground(Main.WHITE); desc.setForeground(Main.WHITE); break;     // convencional
                case 5: setBackground(Main.GREEN); titulo.setForeground(Main.WHITE); desc.setForeground(Main.WHITE); break;   // realista

                default: setBackground(Main.BLACK); titulo.setForeground(Main.WHITE); desc.setForeground(Main.WHITE); break;  // unused (error)
            }
        }
    }
}
