package fase2edd.vista;

import fase2edd.estructuras.arbolavl.ArbolAVL;
import fase2edd.estructuras.arbolavl.NodoAVL;
import fase2edd.estructuras.btree.ArbolB;
import fase2edd.estructuras.btree.NodoB;
import fase2edd.estructuras.bplustree.ArbolBPlus;
import fase2edd.estructuras.bplustree.NodoBPlus;
import fase2edd.estructuras.hash.TablaHash;
import fase2edd.estructuras.hash.NodoHash;
//import fase2edd.modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class VisualizadorGraphviz {

    public static void mostrarAVL(ArbolAVL avl, Component parent) {
        if (avl == null || avl.getRaiz() == null) {
            JOptionPane.showMessageDialog(parent, "El árbol AVL está vacío.");
            return;
        }
        StringBuilder dot = new StringBuilder("digraph AVL {\n");
        dot.append("node [shape=ellipse, fontsize=10];\n");
        generarDotAVL(avl.getRaiz(), dot);
        dot.append("}");
        mostrarGrafico(dot.toString(), "Árbol AVL", parent);
    }

    private static void generarDotAVL(NodoAVL nodo, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        String nombre = nodo.getDato().getNombre().replace("\"", "'");
        sb.append("\"").append(nombre).append("\" [label=\"").append(nombre).append("\"];\n");
        if (nodo.getIzquierdo() != null) {
            String izqNombre = nodo.getIzquierdo().getDato().getNombre().replace("\"", "'");
            sb.append("\"").append(nombre).append("\" -> \"").append(izqNombre).append("\";\n");
            generarDotAVL(nodo.getIzquierdo(), sb);
        }
        if (nodo.getDerecho() != null) {
            String derNombre = nodo.getDerecho().getDato().getNombre().replace("\"", "'");
            sb.append("\"").append(nombre).append("\" -> \"").append(derNombre).append("\";\n");
            generarDotAVL(nodo.getDerecho(), sb);
        }
    }

    public static void mostrarB(ArbolB arbolB, Component parent) {
        if (arbolB == null || arbolB.getRaiz() == null) {
            JOptionPane.showMessageDialog(parent, "El árbol B está vacío.");
            return;
        }
        StringBuilder dot = new StringBuilder("digraph BTree {\n");
        dot.append("node [shape=record, fontsize=10];\n");
        generarDotB(arbolB.getRaiz(), dot);
        dot.append("}");
        mostrarGrafico(dot.toString(), "Árbol B", parent);
    }

    private static void generarDotB(NodoB nodo, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        // Etiqueta con todas las claves del nodo (fechas)
        StringBuilder label = new StringBuilder();
        for (int i = 0; i < nodo.getN(); i++) {
            if (i > 0) {
                label.append("|");
            }
            label.append(nodo.getClaves()[i].getFechaCaducidad());
        }
        // Identificador único para el nodo (usamos el hash del StringBuilder o un contador)
        // Para simplificar, usaremos la concatenación de las claves como ID.
        String nodeId = "node" + nodo.hashCode();
        sb.append("\"").append(nodeId).append("\" [label=\"").append(label.toString()).append("\"];\n");
        if (!nodo.isHoja()) {
            for (int i = 0; i <= nodo.getN(); i++) {
                if (nodo.getHijos()[i] != null) {
                    String hijoId = "node" + nodo.getHijos()[i].hashCode();
                    sb.append("\"").append(nodeId).append("\" -> \"").append(hijoId).append("\";\n");
                    generarDotB(nodo.getHijos()[i], sb);
                }
            }
        }
    }

    public static void mostrarBPlus(ArbolBPlus arbolBPlus, Component parent) {
        if (arbolBPlus == null || arbolBPlus.getRaiz() == null) {
            JOptionPane.showMessageDialog(parent, "El árbol B+ está vacío.");
            return;
        }
        StringBuilder dot = new StringBuilder("digraph BPlusTree {\n");
        dot.append("node [shape=record, fontsize=10];\n");
        // Para B+ solo mostraremos las hojas enlazadas y los nodos internos
        generarDotBPlus(arbolBPlus.getRaiz(), dot);
        dot.append("}");
        mostrarGrafico(dot.toString(), "Árbol B+", parent);
    }

    private static void generarDotBPlus(NodoBPlus nodo, StringBuilder sb) {
        if (nodo == null) {
            return;
        }
        StringBuilder label = new StringBuilder();
        if (nodo.isHoja()) {
            // Mostrar categorías y quizás primer producto
            for (int i = 0; i < nodo.getN(); i++) {
                if (i > 0) {
                    label.append("|");
                }
                label.append(nodo.getClaves()[i]).append("\\n").append(nodo.getProductos()[i].getNombre());
            }
        } else {
            // Solo claves separadoras
            for (int i = 0; i < nodo.getN(); i++) {
                if (i > 0) {
                    label.append("|");
                }
                label.append(nodo.getClaves()[i]);
            }
        }
        String nodeId = "node" + nodo.hashCode();
        sb.append("\"").append(nodeId).append("\" [label=\"").append(label.toString()).append("\"];\n");
        if (!nodo.isHoja()) {
            for (int i = 0; i <= nodo.getN(); i++) {
                if (nodo.getHijos()[i] != null) {
                    String hijoId = "node" + nodo.getHijos()[i].hashCode();
                    sb.append("\"").append(nodeId).append("\" -> \"").append(hijoId).append("\";\n");
                    generarDotBPlus(nodo.getHijos()[i], sb);
                }
            }
        } else {
            // Enlace entre hojas
            if (nodo.getSiguienteHoja() != null) {
                String sigId = "node" + nodo.getSiguienteHoja().hashCode();
                sb.append("\"").append(nodeId).append("\" -> \"").append(sigId).append("\" [style=dotted, color=blue];\n");
            }
        }
    }

    public static void mostrarHash(TablaHash hash, Component parent) {
        if (hash == null) {
            JOptionPane.showMessageDialog(parent, "Tabla hash vacía.");
            return;
        }
        StringBuilder dot = new StringBuilder("digraph HashTable {\n");
        dot.append("rankdir=LR;\n");
        dot.append("node [shape=record, fontsize=10];\n");
        NodoHash[] tabla = hash.getTabla();
        for (int i = 0; i < tabla.length; i++) {
            NodoHash actual = tabla[i];
            if (actual == null) {
                continue;
            }
            // Nodo principal de la cubeta
            String bucketId = "bucket" + i;
            dot.append("\"").append(bucketId).append("\" [label=\"").append(i).append("\"];\n");
            // Lista enlazada
            NodoHash temp = actual;
            int pos = 0;
            String prevId = bucketId;
            while (temp != null) {
                String nodeId = "node" + i + "_" + pos;
                dot.append("\"").append(nodeId).append("\" [label=\"").append(temp.getDato().getCodigoBarra()).append("\"];\n");
                dot.append("\"").append(prevId).append("\" -> \"").append(nodeId).append("\";\n");
                prevId = nodeId;
                temp = temp.getSiguiente();
                pos++;
            }
        }
        dot.append("}");
        mostrarGrafico(dot.toString(), "Tabla Hash", parent);
    }

    private static void mostrarGrafico(String dot, String titulo, Component parent) {
        try {
            MutableGraph g = new Parser().read(dot);
            BufferedImage bufImg = Graphviz.fromGraph(g).width(800).render(Format.PNG).toImage();

            ImageIcon icon = new ImageIcon(bufImg);
            JLabel label = new JLabel(icon);
            JScrollPane scroll = new JScrollPane(label);
            JDialog dialog = new JDialog(SwingUtilities.windowForComponent(parent), titulo, Dialog.ModalityType.APPLICATION_MODAL);
            dialog.add(scroll);
            dialog.setSize(900, 600);
            dialog.setLocationRelativeTo(parent);
            dialog.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error al generar gráfico: " + e.getMessage());
        }
    }
}
