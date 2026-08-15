package com.ponntrix.hospital.service.serviceImpl;

import com.ponntrix.hospital.dto.OperatingHoursDto;
import com.ponntrix.hospital.entity.DayOfWeek;
import com.ponntrix.hospital.entity.EntityType;
import com.ponntrix.hospital.entity.OperatingHours;
import com.ponntrix.hospital.mapper.OperatingHoursMapper;
import com.ponntrix.hospital.repository.DayOfWeekRepository;
import com.ponntrix.hospital.repository.OperatingHoursRepository;

import com.ponntrix.hospital.service.OperatingHoursService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OperatingHoursServiceImpl implements OperatingHoursService {

    private final OperatingHoursRepository operatingHoursRepository;
    private final DayOfWeekRepository dayOfWeekRepository;


    @Override
    @Transactional
    public OperatingHoursDto createOperatingHours(OperatingHoursDto dto) {

        if (operatingHoursRepository
                .existsByEntityTypeAndEntityIdAndDayOfWeekDayOfWeekId(
                        dto.getEntityType(),
                        dto.getEntityId(),
                        dto.getDayOfWeekId())) {

            throw new RuntimeException(
                    "Operating hours already exist for this day."
            );
        }

        validateTimes(dto);

        DayOfWeek dayOfWeek =
                dayOfWeekRepository.findById(
                        dto.getDayOfWeekId()
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Day of week not found."
                        )
                );

        OperatingHours entity = OperatingHoursMapper.toEntity(dto);
        entity.setDayOfWeek(dayOfWeek);
        OperatingHours saved = operatingHoursRepository.save(entity);

        return OperatingHoursMapper.toDto(saved);
    }


    @Override
    public List<OperatingHoursDto> getOperatingHours(EntityType entityType, UUID entityId) {

        return operatingHoursRepository
                .findByEntityTypeAndEntityIdOrderByDayOfWeekDayOfWeekId(
                        entityType,
                        entityId
                )
                .stream()
                .map(OperatingHoursMapper::toDto)
                .toList();
    }


    @Override
    @Transactional
    public OperatingHoursDto updateOperatingHours(
            UUID operatingHoursId, OperatingHoursDto dto) {

        OperatingHours entity =
                operatingHoursRepository.findByOperatingHoursUuid(operatingHoursId)
                        .orElseThrow(() ->
                        new RuntimeException(
                                "Operating hours not found."
                        )
                );

        validateTimes(dto);
        DayOfWeek dayOfWeek = dayOfWeekRepository.findById(dto.getDayOfWeekId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Day of week not found."
                        )
                );
        entity.setEntityType(dto.getEntityType());
        entity.setEntityId(dto.getEntityId());
        entity.setDayOfWeek(dayOfWeek);
        entity.setOpeningTime(dto.getOpeningTime());
        entity.setClosingTime(dto.getClosingTime());
        entity.setIsClosed(dto.getIsClosed());
        entity.setUpdatedBy(dto.getUpdatedBy());
        OperatingHours updated = operatingHoursRepository.save(entity);

        return OperatingHoursMapper.toDto(updated);
    }


    @Override
    @Transactional
    public void deleteOperatingHours(UUID operatingHoursId) {

        OperatingHours entity =
                operatingHoursRepository.findByOperatingHoursUuid(
                        operatingHoursId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Operating hours not found."
                        )
                );

        operatingHoursRepository.delete(entity);
    }


    @Override
    @Transactional
    public void deleteEntityOperatingHours(
            EntityType entityType,
            UUID entityId) {

        operatingHoursRepository
                .deleteByEntityTypeAndEntityId(
                        entityType,
                        entityId
                );
    }


    private void validateTimes(OperatingHoursDto dto) {

        boolean closed = Boolean.TRUE.equals(dto.getIsClosed());

        if (closed) {return;}

        if (dto.getOpeningTime() == null || dto.getClosingTime() == null) {
            throw new RuntimeException("Opening and closing time are required.");
        }
        if (!dto.getClosingTime().isAfter(dto.getOpeningTime())) {
            throw new RuntimeException("Closing time must be after opening time.");
        }
    }
}
