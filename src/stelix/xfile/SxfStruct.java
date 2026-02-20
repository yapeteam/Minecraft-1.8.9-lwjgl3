package stelix.xfile;

import java.util.ArrayList;
import java.util.List;

public class SxfStruct implements ISxfObject {

    private final List<Object> elements = new ArrayList<>();

    public List<Object> elements() {
        return elements;
    }


    public <X> X element(int index) {
        return (X) elements.get(index);
    }

    public <X extends SxfBlock> X elementAsBlock(int index) {
        return element(index);
    }

    public <X extends SxfStruct> X elementAsStruct(int index) {
        return element(index);
    }

}
