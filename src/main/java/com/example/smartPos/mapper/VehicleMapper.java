package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.VehicleResponse;
import com.example.smartPos.repositories.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    VehicleResponse toVehicleResponse(Vehicle vehicle);
}
