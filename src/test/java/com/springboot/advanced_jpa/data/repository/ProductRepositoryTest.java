package com.springboot.advanced_jpa.data.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springboot.advanced_jpa.data.entity.Product;
import com.springboot.advanced_jpa.data.entity.QProduct;
import com.springboot.advanced_jpa.data.repository.support.ProductRepositoryCustom;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager entityManager;

    // 예제 8.26
    @Test
    void queryDslTest() {
        JPAQuery<Product> query = new JPAQuery(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> productList = query
                .from(qProduct)
                .where(qProduct.name.eq("pen"))
                .orderBy(qProduct.price.asc())
                .fetch();

        System.out.println("Found : " + productList.size() );

        for (Product product : productList) {
            System.out.println("----------------");
            System.out.println();
            System.out.println("Product Number : " + product.getNumber());
            System.out.println("Product Name : " + product.getName());
            System.out.println("Product Price : " + product.getPrice());
            System.out.println("Product Stock : " + product.getStock());
            System.out.println();
            System.out.println("----------------");
        }
        System.out.println("DONE!!!");
    }

    @Test
    void queryDslTest2(){
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory( entityManager );
        QProduct qProduct = QProduct.product;

        List<Product> productList = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.name.eq("pen"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for( Product product : productList ){
            System.out.println("----------------");
            System.out.println();
            System.out.println("Product Number : " + product.getNumber());
            System.out.println("Product Name : " + product.getName());
            System.out.println("Product Price : " + product.getPrice());
            System.out.println("Product Stock : " + product.getStock());
            System.out.println();
            System.out.println("----------------");
        }
    }

    @Test
    void queryDslTest3(){
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;

        List<String > productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where( qProduct.name.eq("pen"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for ( String product : productList ){
            System.out.println("-------------");
            System.out.println("Product Name : " + product );
            System.out.println("-------------");
        }

        List<Tuple> tupleList = jpaQueryFactory
                .select( qProduct.name, qProduct.price)
                .from(qProduct)
                .where(qProduct.name.eq("pen"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for(Tuple product : tupleList ){
            System.out.println("-------------");
            System.out.println("Product Name  : " + product.get(qProduct.name)  );
            System.out.println("Product Price : " + product.get(qProduct.price)  );
            System.out.println("-------------");
        }

    }

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    void queryDslTest4(){
        QProduct qProduct = QProduct.product;

        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("pen"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for( String product : productList ){
            System.out.println("-------------");
            System.out.println("Product Name : " + product );
            System.out.println("-------------");
        }
    }

    @Test
    public void auditingTest(){
        Product product = new Product();
        product.setName("pencil");
        product.setPrice(1000);
        product.setStock(100);

        Product savedProduct = productRepository.save(product);

        System.out.println("productName : " + savedProduct.getName());
        System.out.println("createdAt : " + savedProduct.getCreatedAt());
    }


}
