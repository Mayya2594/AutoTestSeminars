package org.max.seminar_4_HW;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest extends AbstractTest{

    @Test
    @Order(1)
    void getProducts_whenValid_shouldReturn() throws SQLException {
        //given
        String sql = "SELECT * FROM products WHERE price = 300";
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;
        }
        final Query query = getSession().createSQLQuery("SELECT * FROM products").addEntity(ProductsEntity.class);
        //then
        Assertions.assertEquals(1, countTableSize);
        Assertions.assertEquals(10, query.list().size());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"GOJIRA ROLL, 300", "VIVA LAS VEGAS ROLL, 450", "FUTOMAKI, 700"})
    void getProductsById_whenValid_shouldReturn(String name, float price) throws SQLException {
        //given
        String sql = "SELECT * FROM products WHERE menu_name='" + name + "'";
        Statement stmt = getConnection().createStatement();
        float menuPrice = 0;
        float resultMenuPrice = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            menuPrice = rs.getFloat(3);
            resultMenuPrice = rs.getFloat("price");
        }
        //then
        Assertions.assertEquals(price, menuPrice);
        Assertions.assertEquals(price, resultMenuPrice);
    }

    @Test
    @Order(3)
    void addProduct_whenValid_shouldSave() {
        //given
        ProductsEntity entity = new ProductsEntity();
        entity.setProductId((short) 11);
        entity.setMenuName("WHITE RUSSIAN");
        entity.setPrice("600");
        //when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id=" + 11)
                .addEntity(ProductsEntity.class);
        ProductsEntity productsEntity = (ProductsEntity) query.uniqueResult();
        //then
        Assertions.assertNotNull(productsEntity);
        Assertions.assertEquals("600.0", productsEntity.getPrice());
    }

    @Test
    @Order(4)
    void deleteProduct_whenValid_shouldDelete() {
        //given
        final Query query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id=" + 11)
                .addEntity(ProductsEntity.class);
        Optional<ProductsEntity> productsEntity = (Optional<ProductsEntity>) query.uniqueResultOptional();
        Assumptions.assumeTrue(productsEntity.isPresent());
        //when
        Session session = getSession();
        session.beginTransaction();
        session.delete(productsEntity.get());
        session.getTransaction().commit();
        //then
        final Query queryAfterDelete = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id=" + 11)
                .addEntity(ProductsEntity.class);
        Optional<ProductsEntity> productsEntityAfterDelete = (Optional<ProductsEntity>) queryAfterDelete.uniqueResultOptional();
        Assertions.assertFalse(productsEntityAfterDelete.isPresent());
    }
}
