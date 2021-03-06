package hotelSystem.reservation.Service;

import hotelSystem.reservation.domain.Customer;
import hotelSystem.reservation.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Long join(Customer customer){
        validateDuplicateCustomer(customer);
        customerRepository.save(customer);
        return customer.getId();
    }

    public void validateDuplicateCustomer(Customer customer){
        List<Customer> result = customerRepository.findByNameAndPhoneNumber(customer);
        if(!result.isEmpty()){
            throw new IllegalStateException("이미 존재하는 고객입니다.");
        }
    }

    public Customer findCustomer(Long id){
        return customerRepository.findOne(id);
    }

    public List<Customer> findCustomers(){
        return customerRepository.findAll();
    }


}
