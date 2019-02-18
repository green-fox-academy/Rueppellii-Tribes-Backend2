package rueppellii.backend2.tribes.common;

public interface Upgradeable<T> {

    void upgrade(T t);
    void create();
}
