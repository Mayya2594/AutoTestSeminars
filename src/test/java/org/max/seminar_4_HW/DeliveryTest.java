package org.max.seminar_4_HW;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.PersistenceException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryTest extends AbstractTest {

    @Test
    @Order(1)
    void getDeliveries_whenValid_shouldReturn() throws SQLException {
        //given
        String sql = "SELECT * FROM delivery";
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;
        }
        final Query query = getSession().createSQLQuery("SELECT * FROM delivery").addEntity(DeliveryEntity.class);
        //then
        Assertions.assertEquals(15, countTableSize);
        Assertions.assertEquals(15, query.list().size());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"1, Cash", "2, Card", "7, NULL"})
    void getProductsById_whenValid_shouldReturn(int deliveryId, String payment) throws SQLException {
        //given
        String sql = "SELECT * FROM delivery WHERE delivery_id=" + deliveryId;
        Statement stmt = getConnection().createStatement();
        String paymentType = "";
        String resultPaymentType = "";
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            paymentType = rs.getString(6);
            resultPaymentType = rs.getString("payment_method");
        }
        //then
        Assertions.assertEquals(payment, paymentType);
        Assertions.assertEquals(payment, resultPaymentType);
    }

    @Test
    @Order(3)
    void addDelivery_whenNotValid_shouldSave() {
        //given
        DeliveryEntity entity = new DeliveryEntity();
        entity.setDeliveryId((short) 16);
        entity.setDateArrived("2024-04-08 22:25:25");
        entity.setTaken("NO");
        entity.setPaymentMethod("NULL");
        //when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        //then
        Assertions.assertThrows(PersistenceException.class, () -> session.getTransaction().commit());
    }
}
