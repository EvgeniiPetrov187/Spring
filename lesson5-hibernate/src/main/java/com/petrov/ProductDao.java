package com.petrov;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ProductDao {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Product product = new Product(null, "Sausage", 40);

        entityManager.getTransaction().begin();

        findById(2L, entityManager); // поиск продукта по ID

        findAll(entityManager); // вывод списка всех продуктов

        updateOrSave(product, entityManager); // обновление информации о продукте или добавление нового продукта

        deleteById(5L, entityManager); // удаление продукта по ID

        entityManager.close();
    }

    // поиск продукта по ID
    public static void findById(Long id, EntityManager entityManager) {
        Product product = entityManager.find(Product.class, id);
        entityManager.getTransaction().commit();
        System.out.println(product.toString());
    }

    // удаление продукта по ID
    public static void deleteById(Long id, EntityManager entityManager) {
        Product product = entityManager.find(Product.class, id);
        entityManager.remove(product);
        entityManager.getTransaction().commit();
    }

    // вывод списка всех продуктов
    public static List findAll(EntityManager entityManager) {
        List list = entityManager.createNamedQuery("AllProducts")
                .getResultList();
        entityManager.getTransaction().commit();
        System.out.println(list);
        return list;
    }

    // обновление информации о продукте или добавление нового продукта
    public static void updateOrSave(Product product, EntityManager entityManager) {
        if (product.getId() == null) {
            entityManager.persist(product);
        } else {
            entityManager.merge(product);
        }
        entityManager.getTransaction().commit();
    }
}
