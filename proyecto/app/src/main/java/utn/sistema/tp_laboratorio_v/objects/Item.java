package utn.sistema.tp_laboratorio_v.objects;

import java.util.Objects;

/**
 * Item de la lista de gastos
 * Producto o servicio
 */
public class Item
{
    private Integer id;
    private String descripcion;
    private Double valor;
    private Categoria categoria;

    public Item(Integer id, String descripcion, Double valor, Categoria categoria)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.valor = valor;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("id=").append(id);
        sb.append(", descripcion='").append(descripcion).append('\'');
        sb.append(", valor=").append(valor);
        sb.append(", categoria=").append(categoria);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId()) && Objects.equals(getDescripcion(),
                item.getDescripcion()) && Objects.equals(getValor(),
                item.getValor()) && Objects.equals(getCategoria(), item.getCategoria());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescripcion(), getValor(), getCategoria());
    }
}
