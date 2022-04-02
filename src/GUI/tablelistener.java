package GUI;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class tablelistener implements TableModelListener {

    @Override
    public void tableChanged(TableModelEvent e) {
        tablemodel modelx= (tablemodel) e.getSource();
        System.out.println(modelx.getValueAt(e.getFirstRow(),e.getColumn()));

    }


}
