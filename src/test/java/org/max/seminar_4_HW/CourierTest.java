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
public class CourierTest extends AbstractTest{

    @Test
    @Order(1)
    void getCouriers_whenValid_shouldReturn() throws SQLException {
        //given
        String sql = "SELECT * FROM courier_info";
        Statement stmt = getConnection().createStatement();
        int countTableSize = 0;
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            countTableSize++;
        }
        final Query query = getSession().createSQLQuery(sql).addEntity(CourierInfoEntity.class);
        //then
        Assertions.assertEquals(4, countTableSize);
        Assertions.assertEquals(4, query.list().size());
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({"John, foot", "Kate, car", "Bob, car"})
    void getCourierById_whenValid_shouldReturn(String name, String delivery) throws SQLException {
        //given
        String sql = "SELECT * FROM courier_info WHERE first_name='" + name + "'";
        Statement stmt = getConnection().createStatement();
        String deliveryType = "";
        String resultDeliveryType = "";
        //when
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            deliveryType = rs.getString(5);
            resultDeliveryType = rs.getString("delivery_type");
        }
        //then
        Assertions.assertEquals(delivery, deliveryType);
        Assertions.assertEquals(delivery, resultDeliveryType);
    }

    @Test
    @Order(3)
    void addCourier_whenValid_shouldSave() {
        //given
        CourierInfoEntity entity = new CourierInfoEntity();
        entity.setCourierId((short) 5);
        entity.setFirstName("Dominic");
        entity.setLastName("Toretto");
        entity.setPhoneNumber("+7 960 123 7801");
        entity.setDeliveryType("Dodge Charger R/T");
        //when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query query = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE courier_id=" + 5)
                .addEntity(CourierInfoEntity.class);
        CourierInfoEntity courierInfoEntity = (CourierInfoEntity) query.uniqueResult();
        //then
        Assertions.assertNotNull(courierInfoEntity);
        Assertions.assertEquals("Dodge Charger R/T", courierInfoEntity.getDeliveryType());
    }

    @Test
    @Order(4)
    void deleteCourier_whenValid_shouldDelete() {
        //given
        final Query query = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE courier_id=" + 5)
                .addEntity(CourierInfoEntity.class);
        Optional<CourierInfoEntity> courierInfoEntity = (Optional<CourierInfoEntity>) query.uniqueResultOptional();
        Assumptions.assumeTrue(courierInfoEntity.isPresent());
        //when
        Session session = getSession();
        session.beginTransaction();
        session.delete(courierInfoEntity.get());
        session.getTransaction().commit();
        //then
        final Query queryAfterDelete = getSession()
                .createSQLQuery("SELECT * FROM courier_info WHERE courier_id=" + 5)
                .addEntity(CourierInfoEntity.class);
        Optional<CourierInfoEntity> courierInfoEntityAfterDelete = (Optional<CourierInfoEntity>) queryAfterDelete.uniqueResultOptional();
        Assertions.assertFalse(courierInfoEntityAfterDelete.isPresent());
    }
}
