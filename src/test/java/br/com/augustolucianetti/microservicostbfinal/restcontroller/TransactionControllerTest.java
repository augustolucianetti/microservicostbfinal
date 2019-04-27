package br.com.augustolucianetti.microservicostbfinal.restcontroller;

import br.com.augustolucianetti.microservicostbfinal.model.Statistic;
import br.com.augustolucianetti.microservicostbfinal.model.Transaction;
import br.com.augustolucianetti.microservicostbfinal.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsController.class)
public class TransactionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TransactionRepository transactionRepository;

    Transaction transaction;

    Long timestamp;

    Long timeEquals;

    @Before
    public void setUp() {
        timestamp = System.currentTimeMillis() - 3l;
        transaction = new Transaction();
        transaction.setAmount( 200d );
        timeEquals =  System.currentTimeMillis();
        transaction.setTimestamp( timeEquals );
    }

    @Test
    public void transactionSucess() throws Exception {

        transactionRepository.addTransaction(transaction.getAmount(), transaction.getTimestamp());
        mvc.perform(post("/transactions/?timestamp=" + timestamp + "&amount=200.00")
                .accept( MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(response -> {
                    int status = response.getResponse().getStatus();
                    Assert.assertEquals( 201, status );
                    Assert.assertNotNull( this.transaction );
                    Assert.assertEquals( timeEquals, transaction.getTimestamp() );
                    Assert.assertEquals( new Double( 200 ), transaction.getAmount() );
                });
    }

    @Test
    public void transactionFailed() throws Exception {
        timestamp = timestamp - 60000;
        transactionRepository.addTransaction(transaction.getAmount(), transaction.getTimestamp());
        mvc.perform(post("/transactions/?timestamp=" + timestamp + "&amount=200.00")
                .accept( MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void statisticsSucess() throws Exception {
        Map<Long, Double> map = new HashMap<Long, Double>();


        Mockito.doAnswer( new Answer() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                map.put( invocationOnMock.getArgument( 1 ), invocationOnMock.getArgument( 0 ) );
                return null;
            }
        } ).when( transactionRepository ).addTransaction( Mockito.anyDouble(), Mockito.anyLong() );

        transactionRepository.addTransaction( transaction.getAmount(), transaction.getTimestamp() );
        transactionRepository.addTransaction( transaction.getAmount(), transaction.getTimestamp()+1 );
        transactionRepository.addTransaction( transaction.getAmount(), transaction.getTimestamp()+2 );
        transactionRepository.addTransaction( transaction.getAmount(), transaction.getTimestamp()+3 );
        transactionRepository.addTransaction( transaction.getAmount(), transaction.getTimestamp()+4 );

        Mockito.doAnswer( new Answer() {
            @Override
            public Map<Long, Double> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return map;
            }
        } ).when( transactionRepository ).getTransactions();

        mvc.perform(get("/statistics")
                .accept( MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(response -> {
                    int status = response.getResponse().getStatus();
                    String json = response.getResponse().getContentAsString();
                    Statistic statistic = new ObjectMapper().readValue(json, Statistic.class);
                    Assert.assertEquals( 200, status );
                    Assert.assertNotNull( statistic );
                    Assert.assertEquals( 5, statistic.getCount().longValue() );
                    Assert.assertEquals( 200.0, statistic.getAvg().doubleValue(), 0.0001);
                    Assert.assertEquals( 200.0 ,statistic.getMax().longValue(), 0.0001 );
                    Assert.assertEquals( 200.0,statistic.getMin().longValue(), 0.0001 );
                    Assert.assertEquals( 1000,statistic.getSum().longValue(), 0.0001 );
                });
    }

    @Test
    public void statisticsFailed() throws Exception{

        mvc.perform(get("/statistics")
                .accept( MediaType.APPLICATION_JSON))
                .andExpect(response -> {
                    int status = response.getResponse().getStatus();
                    Assert.assertEquals( 404, status );
                });
    }
}
