/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 *
 * @author Leviv
 */
public class HittableList implements Hittable, List<Hittable>, Cloneable {

    ArrayList<Hittable> hObjects;

    public HittableList() {
        this.hObjects = new ArrayList<>();
    }

    public HittableList(Hittable hObject) {
        this();
        this.hObjects.add(hObject);
    }

    public void trimToSize() {
        hObjects.trimToSize();
    }

    public void ensureCapacity(int minCapacity) {
        hObjects.ensureCapacity(minCapacity);
    }

    @Override
    public int size() {
        return hObjects.size();
    }

    @Override
    public boolean isEmpty() {
        return hObjects.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return hObjects.contains(o);
    }

    @Override
    public int indexOf(Object o) {
        return hObjects.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return hObjects.lastIndexOf(o);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        HittableList h = (HittableList) super.clone(); //To change body of generated methods, choose Tools | Templates.
        h.hObjects = (ArrayList<Hittable>) this.hObjects.clone();
        return h;
    }

    @Override
    public Object[] toArray() {
        return hObjects.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return hObjects.toArray(a);
    }

    @Override
    public Hittable get(int index) {
        return hObjects.get(index);
    }

    @Override
    public Hittable set(int index, Hittable element) {
        return hObjects.set(index, element);
    }

    @Override
    public boolean add(Hittable e) {
        return hObjects.add(e);
    }

    @Override
    public void add(int index, Hittable element) {
        hObjects.add(index, element);
    }

    @Override
    public Hittable remove(int index) {
        return hObjects.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return hObjects.remove(o);
    }

    @Override
    public void clear() {
        hObjects.clear();
    }

    @Override
    public boolean addAll(Collection<? extends Hittable> c) {
        return hObjects.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Hittable> c) {
        return hObjects.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return hObjects.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return hObjects.retainAll(c);
    }

    @Override
    public ListIterator<Hittable> listIterator(int index) {
        return hObjects.listIterator(index);
    }

    @Override
    public ListIterator<Hittable> listIterator() {
        return hObjects.listIterator();
    }

    @Override
    public Iterator<Hittable> iterator() {
        return hObjects.iterator();
    }

    @Override
    public List<Hittable> subList(int fromIndex, int toIndex) {
        return hObjects.subList(fromIndex, toIndex);
    }

    @Override
    public void forEach(Consumer<? super Hittable> action) {
        hObjects.forEach(action);
    }

    @Override
    public Spliterator<Hittable> spliterator() {
        return hObjects.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super Hittable> filter) {
        return hObjects.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<Hittable> operator) {
        hObjects.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super Hittable> c) {
        hObjects.sort(c);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HittableList other = (HittableList) obj;
        if (!Objects.equals(this.hObjects, other.hObjects)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.hObjects);
        return hash;
    }

    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord recOut) {
        HitRecord tempRec = new HitRecord();
        double closestSoFar = tMax;
        boolean hitAnything = false;
        for (Hittable obj : this.hObjects) {
            if (obj.hit(r, tMin, closestSoFar, tempRec)) {
                closestSoFar = tempRec.t;
                recOut.copy(tempRec);
                hitAnything = true;
            }
        }
        return hitAnything;
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        return this.hObjects.containsAll(arg0);
    }

}
