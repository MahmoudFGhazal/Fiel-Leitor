package com.mahas.facade;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mahas.command.post.IPostCommand;
import com.mahas.command.pre.IPreCommand;
import com.mahas.dao.IDAO;
import com.mahas.dao.address.AddressDAO;
import com.mahas.dao.address.ResidenceTypeDAO;
import com.mahas.dao.address.StreetTypeDAO;
import com.mahas.dao.product.BookDAO;
import com.mahas.dao.product.CartDAO;
import com.mahas.dao.product.CategoryDAO;
import com.mahas.dao.sale.PromotionalCouponDAO;
import com.mahas.dao.sale.SaleBookDAO;
import com.mahas.dao.sale.SaleCardDAO;
import com.mahas.dao.sale.SaleDAO;
import com.mahas.dao.sale.StatusSaleDAO;
import com.mahas.dao.sale.TraderCouponDAO;
import com.mahas.dao.user.CardDAO;
import com.mahas.dao.user.GenderDAO;
import com.mahas.dao.user.UserDAO;
import com.mahas.domain.DataResponse;
import com.mahas.domain.DomainEntity;
import com.mahas.domain.FacadeRequest;
import com.mahas.domain.FacadeResponse;
import com.mahas.domain.SQLRequest;
import com.mahas.domain.SQLResponse;
import com.mahas.domain.address.Address;
import com.mahas.domain.address.ResidenceType;
import com.mahas.domain.address.StreetType;
import com.mahas.domain.product.Book;
import com.mahas.domain.product.Cart;
import com.mahas.domain.product.Category;
import com.mahas.domain.sale.PromotionalCoupon;
import com.mahas.domain.sale.Sale;
import com.mahas.domain.sale.SaleBook;
import com.mahas.domain.sale.SaleCard;
import com.mahas.domain.sale.StatusSale;
import com.mahas.domain.sale.TraderCoupon;
import com.mahas.domain.user.Card;
import com.mahas.domain.user.Gender;
import com.mahas.domain.user.User;
import com.mahas.dto.response.DTOResponse;
import com.mahas.dto.response.address.AddressDTOResponse;
import com.mahas.dto.response.address.ResidenceTypeDTOResponse;
import com.mahas.dto.response.address.StreetTypeDTOResponse;
import com.mahas.dto.response.product.BookDTOResponse;
import com.mahas.dto.response.product.CartDTOResponse;
import com.mahas.dto.response.product.CategoryDTOResponse;
import com.mahas.dto.response.sale.PromotionalCouponDTOResponse;
import com.mahas.dto.response.sale.SaleBookDTOResponse;
import com.mahas.dto.response.sale.SaleCardDTOResponse;
import com.mahas.dto.response.sale.SaleDTOResponse;
import com.mahas.dto.response.sale.StatusSaleDTOResponse;
import com.mahas.dto.response.sale.TraderCouponDTOResponse;
import com.mahas.dto.response.user.CardDTOResponse;
import com.mahas.dto.response.user.GenderDTOResponse;
import com.mahas.dto.response.user.UserDTOResponse;

import jakarta.annotation.PostConstruct;

public abstract class FacadeAbstract {

    //User
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GenderDAO genderDAO;

    @Autowired
    private CardDAO cardDAO;

    //Address
    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private ResidenceTypeDAO residenceTypeDAO;

    @Autowired
    private StreetTypeDAO streetTypeDAO;

    //Sale
    @Autowired
    private SaleDAO saleDAO;

    @Autowired
    private SaleBookDAO saleBookDAO;

    @Autowired
    private SaleCardDAO saleCardDAO;

    @Autowired
    private PromotionalCouponDAO promotionalCouponDAO;

    @Autowired
    private TraderCouponDAO traderCouponDAO;

    @Autowired
    private StatusSaleDAO statusSaleDAO;

    //User
    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    protected final Map<String, IDAO> daos = new HashMap<>();

    protected final Map<String, Class<? extends DTOResponse>> dtos = new HashMap<>();

    @PostConstruct
    public void init() {
        initDaos();
        initDtos();
    }

    private void initDaos() {
        daos.put(User.class.getName(), userDAO);
        daos.put(Gender.class.getName(), genderDAO);
        daos.put(Address.class.getName(), addressDAO);
        daos.put(ResidenceType.class.getName(), residenceTypeDAO);
        daos.put(StreetType.class.getName(), streetTypeDAO);
        daos.put(Card.class.getName(), cardDAO);
        daos.put(Book.class.getName(), bookDAO);
        daos.put(Cart.class.getName(), cartDAO);
        daos.put(Category.class.getName(), categoryDAO);
        daos.put(PromotionalCoupon.class.getName(), promotionalCouponDAO);
        daos.put(Sale.class.getName(), saleDAO);
        daos.put(SaleBook.class.getName(), saleBookDAO);
        daos.put(SaleCard.class.getName(), saleCardDAO);
        daos.put(StatusSale.class.getName(), statusSaleDAO);
        daos.put(TraderCoupon.class.getName(), traderCouponDAO);
    }

    
    private void initDtos() {
        dtos.put(User.class.getName(), UserDTOResponse.class);
        dtos.put(Gender.class.getName(), GenderDTOResponse.class);
        dtos.put(Address.class.getName(), AddressDTOResponse.class);
        dtos.put(ResidenceType.class.getName(), ResidenceTypeDTOResponse.class);
        dtos.put(StreetType.class.getName(), StreetTypeDTOResponse.class);
        dtos.put(Card.class.getName(), CardDTOResponse.class);
        dtos.put(Book.class.getName(), BookDTOResponse.class);
        dtos.put(Cart.class.getName(), CartDTOResponse.class);
        dtos.put(Category.class.getName(), CategoryDTOResponse.class);
        dtos.put(PromotionalCoupon.class.getName(), PromotionalCouponDTOResponse.class);
        dtos.put(Sale.class.getName(), SaleDTOResponse.class);
        dtos.put(SaleBook.class.getName(), SaleBookDTOResponse.class);
        dtos.put(SaleCard.class.getName(), SaleCardDTOResponse.class);
        dtos.put(StatusSale.class.getName(), StatusSaleDTOResponse.class);
        dtos.put(TraderCoupon.class.getName(), TraderCouponDTOResponse.class);
    }

    protected SQLRequest runRulesRequest(FacadeRequest request){
        IPreCommand command = request.getPreCommand(); 
        if(command == null) return null;

        return command.execute(request);
    }

    protected FacadeResponse prepareResponse(FacadeRequest request, SQLResponse sqlResponse){
        IPostCommand command = request.getPostCommand(); 
        if (command != null) return command.execute(sqlResponse);

        FacadeResponse response = new FacadeResponse();
        DataResponse data = executeDefault(sqlResponse);
        response.setData(data);

        return response;
    }

    private DataResponse executeDefault(SQLResponse sqlResponse) {
        DataResponse response = new DataResponse();

        DomainEntity entity = sqlResponse.getEntity();
        if (entity != null) {
            DTOResponse dto = createDTOFromEntity(entity);
            if (dto != null) {
                response.setEntity(dto);
            }
        }

        if (sqlResponse.getEntities() != null && !sqlResponse.getEntities().isEmpty()) {
            List<DTOResponse> dtoList = new ArrayList<>();
            for (DomainEntity e : sqlResponse.getEntities()) {
                DTOResponse dto = createDTOFromEntity(e);
                if (dto != null) {
                    dtoList.add(dto);
                }
            }
            if (!dtoList.isEmpty()) {
                response.setEntities(dtoList);
            }
        }

        response.setLimit(sqlResponse.getLimit());
        response.setPage(sqlResponse.getPage());
        response.setTotalItem(sqlResponse.getTotalItem());
        response.setTotalPage(sqlResponse.getTotalPage());

        return response;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private DTOResponse createDTOFromEntity(DomainEntity entity) {
        Class<? extends DTOResponse> dtoClass = dtos.get(entity.getClass().getName());

        if (dtoClass != null) {
            try {
                DTOResponse dto = dtoClass.getDeclaredConstructor().newInstance();
                dto.mapFromEntity(entity);

                return dto;
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } 

        return null;
    }
}