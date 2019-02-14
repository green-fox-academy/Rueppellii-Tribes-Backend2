package rueppellii.backend2.tribes.enumToTable;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;

import java.io.Serializable;

public class EnumTuplizer extends PojoEntityTuplizer {
    public EnumTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity) {
        super(entityMetamodel, mappedEntity);
    }

    public Instantiator buildInstantiator(final PersistentClass persistentClass) {
        return new Instantiator() {
            @Override
            public Object instantiate(Serializable id) {
                try {
                    return Enum.valueOf(
                            (Class) persistentClass.getClass().getClassLoader().loadClass(persistentClass.getClassName()),
                            (String) id
                    );
                } catch (ClassNotFoundException e) {
                    throw new AssertionError(e);
                }
            }

            @Override
            public Object instantiate() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isInstance(Object object) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
