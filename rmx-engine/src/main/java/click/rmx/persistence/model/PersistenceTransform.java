package click.rmx.persistence.model;

import click.rmx.core.HierarchyObject;
import click.rmx.engine.components.ANodeComponent;
import click.rmx.engine.components.Node;
import click.rmx.engine.math.Float3;
import click.rmx.engine.math.Matrix4;
import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 03/10/2015.
 */
@Entity(name = "transform")
public abstract class PersistenceTransform extends ANodeComponent implements HierarchyObject {

    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

//    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    private Node node;

    @OneToMany
    private List<PersistenceTransform> children = new ArrayList<>();

    @ManyToOne
    private PersistenceTransform parent;

    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "matrix_history")
    private List<Matrix4Buffer> history = new ArrayList<>();

    @NotNull
    @Column(name = "local_matrix")
    private float[] localMatrixBuffer;

    @NotNull
    @Column(name = "axis")
    private float[] axisBuffer;

    @Column(name = "scale")
    private float[] scaleBuffer;

    @Transient
    private final Float3 scaleTupple = new Float3(null);

    @Transient
    private final Matrix4 axis = Matrix4.Identity();

    @Transient
    private final Matrix4 localMatrix = Matrix4.Identity();

    protected PersistenceTransform(Node node) {
        super();
        this.node = node;
        this.axisBuffer = Matrix4Buffer.Identity();
        this.localMatrixBuffer = Matrix4Buffer.Identity();
        this.scaleBuffer = new float[] {
                1, 1, 1
        };
        this.scaleTupple.setTuple(scaleBuffer);
//        this.children = new ArrayList<>();
    }

    public PersistenceTransform(){
        super();
    }

    public void setScale(float x, float y, float z) {
        scaleBuffer[0] = x;
        scaleBuffer[1] = y;
        scaleBuffer[2] = z;
        //TODO: Confirm this changes scaleTuple as well.
    }


    public PersistenceTransform getParent() {
        return this.parent;
    }

    @Override
    public List<PersistenceTransform> getChildren() {
        return this.children;
    }

    public boolean isGameRoot() {
        return this.isRoot();
    }

    public boolean isNodeRoot() {
        return !this.isRoot() && this.parent.isRoot();
    }





    public boolean removeChild(Node node) {
        return removeChild(node.transform());
    }

    public boolean removeChild(PersistenceTransform child) {
        if (child.parent != this )//(!this.children.contains(child))
            return false;
        else {
            this.children.remove(child);
            child.parent = null;
            return true;
        }
    }

    public void setParent(Node node) {
        this.setParent(node.getTransform());
    }

    @Override
    public void setParent(HierarchyObject parent) {
        if (this.parent == parent)
            return;
        if (this.parent != null)
            this.parent.getChildren().remove(this);
        this.parent = (PersistenceTransform) parent;
        parent.getChildren().add(this);
    }

    public Matrix4 localMatrix() {
        return localMatrix;
    }

    public void setLocalMatrixBuffer(float[] localMatrixBuffer) {
        Matrix4.setElements(this.localMatrix, this.localMatrixBuffer = localMatrixBuffer);
    }



    public void setNode(Node node) {
        if (!node.equals(this.node))
            throw new IllegalArgumentException("Transform can only be assigned once");
        this.node = node;
    }

    public void update() {
        history.add(new Matrix4Buffer()
                .setCurrent(false)
                .setElements(localMatrixBuffer.clone())
        );
    }

    public Float3 scale() {
        return scaleTupple;
    }

    public float[] getScaleBuffer() {
        return scaleBuffer;
    }

    public void setScaleBuffer(float[] scaleBuffer) {
        this.scaleTupple.setTuple(this.scaleBuffer = scaleBuffer);
    }


    public Matrix4 axis() {
        return axis;
    }

    public float[] getAxisBuffer() {
        return axisBuffer;
    }

    public void setAxisBuffer(float[] axisBuffer) {
        this.axisBuffer = axisBuffer;
    }

    public void setHistory(List<Matrix4Buffer> history) {
        this.history = history;
    }

    public List<Matrix4Buffer> getHistory() {
        return history;
    }

    public Node getNode() {
        return node;
    }



    public abstract float getHeight();

    public abstract Matrix4 worldMatrix();
}

