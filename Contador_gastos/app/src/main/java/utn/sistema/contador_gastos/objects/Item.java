package utn.sistema.contador_gastos.objects;

import java.util.Objects;

/**
 * Item de la lista de gastos
 * Producto o servicio
 */
public class Item
{
    private Integer id;
    private String description;
    private Double prize;
    private String category;
    private String date;

    public Item(String description, Double prize, String category, String date)
    {
        this.description = description;
        this.prize = prize;
        this.category = category;
        this.date = date;
    }

    public Item(Integer id, String description, Double prize, String category, String date)
    {
        this.id = id;
        this.description = description;
        this.prize = prize;
        this.category = category;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("id=").append(id);
        sb.append(", description='").append(description).append('\'');
        sb.append(", prize=").append(prize);
        sb.append(", category='").append(category).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId()) && Objects.equals(getDescription(), item.getDescription()) && Objects.equals(getPrize(), item.getPrize()) && Objects.equals(category, item.category) && Objects.equals(getDate(), item.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getPrize(), category, getDate());
    }


}
