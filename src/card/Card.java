package card;

public class Card
{
    private final int tipo;
    private final String titulo;
    private final String desc;
    private CardPanel panel;
    
    public Card(int tipo, String titulo, String desc)
    {
        this.tipo = tipo;
        this.titulo = titulo;
        this.desc = desc;
        initPanel();
    }
    
    private void initPanel()
    {
        panel = new CardPanel(this);
    }
    
    public int getTipo() { return tipo; }
    public String getTitulo() { return titulo; }
    public String getDesc() { return desc; }
    public CardPanel getPanel() { return panel; }
}
