package ru.aisa.carwash.service;

import org.springframework.stereotype.Service;
import ru.aisa.carwash.model.WashOption;
import ru.aisa.carwash.repository.WashOptionRepository;

import java.util.List;

@Service
public class WashOptionService {
    final
    WashOptionRepository washOptionRepository;

    public WashOptionService(WashOptionRepository washOptionRepository) {
        this.washOptionRepository = washOptionRepository;
    }

    public List<WashOption> findAll() {
        return washOptionRepository.findAll();
    }

    public WashOption findId(Long id) {
        return washOptionRepository.getOne(id);
    }

    public boolean save(WashOption washOption) {
        WashOption washOptionFromDB = washOptionRepository.findByName(washOption.getName());
        if (washOptionFromDB != null) {
            return false;
        }
        washOptionRepository.save(washOption);
        return true;
    }

    public void update(WashOption washOption) {
        washOptionRepository.updateWashOption(washOption.getId(), washOption.getName(), washOption.getCost(), washOption.getTime());
    }

    public void delete(Long id) {
        washOptionRepository.deleteById(id);
    }
}
