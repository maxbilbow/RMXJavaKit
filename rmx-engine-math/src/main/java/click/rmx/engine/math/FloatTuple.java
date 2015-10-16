package click.rmx.engine.math;

/**
 * Created by Max on 03/10/2015.
 */
public abstract class FloatTuple {
    protected float[] tuple;


    public void setTuple(float[] tuple) {
        if (tuple.length != size())
            throw new ArrayIndexOutOfBoundsException("" +
                    "Tuple expected array of size " + size() +
                    " but got received array of size " + tuple.length);
        this.tuple = tuple;
    }

    /**
     *
     * @param tuple must equal size(). Null is permissible when planed to be assigned later.
     */
    public FloatTuple(float[] tuple) {
        if (tuple != null)
            this.setTuple(tuple);
        else
            this.tuple = null;
    }

    private FloatTuple() {
        this.tuple = null;
    }

    protected abstract int size();

    public float average() {
        float total = 0;
        for (float n : tuple)
            total += n;
        return total / tuple.length;
    }

    public double length()
    {
        double length = 0;
        for (float n : tuple)
            length += n * n;
        return Math.sqrt(length);
    }
}
