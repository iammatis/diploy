package sk.vilk.diploy.collections;

import sk.vilk.diploy.PersistenceManager;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LazyList implements List
{
    private List list;
    private String mappedBy;
    private Object entityId;
    private List<String> foreignIds;
    private PersistenceManager persistenceManager;

    public LazyList(String mappedBy, Object entityId, List<String> foreignIds, PersistenceManager persistenceManager) {
        this.mappedBy = mappedBy;
        this.entityId = entityId;
        this.foreignIds = foreignIds;
        this.persistenceManager = persistenceManager;
    }

    private void loadRelations() {
        if (list == null) {
            list = persistenceManager.findAll(mappedBy, entityId, foreignIds);
        }
    }

    @Override
    public int size() {
        loadRelations();
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        loadRelations();
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        loadRelations();
        return list.contains(o);
    }

    @Override
    public Iterator iterator() {
        loadRelations();
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        loadRelations();
        return list.toArray();
    }

    @Override
    public boolean add(Object o) {
        loadRelations();
        return list.add(o);
    }

    @Override
    public boolean remove(Object o) {
        loadRelations();
        return list.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        loadRelations();
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        loadRelations();
        return list.addAll(index, c);
    }

    @Override
    public void clear() {
        loadRelations();
        list.clear();
    }

    @Override
    public Object get(int index) {
        loadRelations();
        return list.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        loadRelations();
        return list.set(index, element);
    }

    @Override
    public void add(int index, Object element) {
        loadRelations();
        list.add(index, element);
    }

    @Override
    public Object remove(int index) {
        loadRelations();
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        loadRelations();
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        loadRelations();
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        loadRelations();
        return list.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        loadRelations();
        return list.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        loadRelations();
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public boolean retainAll(Collection c) {
        loadRelations();
        return list.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        loadRelations();
        return list.removeAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        loadRelations();
        return list.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a) {
        loadRelations();
        return list.toArray(a);
    }
}
