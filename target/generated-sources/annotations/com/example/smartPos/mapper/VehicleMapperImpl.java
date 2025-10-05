package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.VehicleResponse;
import com.example.smartPos.repositories.model.Vehicle;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-04T08:37:20+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class VehicleMapperImpl implements VehicleMapper {

    @Override
    public VehicleResponse toVehicleResponse(Vehicle vehicle) {
        if ( vehicle == null ) {
            return null;
        }

        VehicleResponse vehicleResponse = new VehicleResponse();

        vehicleResponse.setId( vehicle.getId() );
        vehicleResponse.setName( vehicle.getName() );
        vehicleResponse.setMake( vehicle.getMake() );
        vehicleResponse.setModel( vehicle.getModel() );
        vehicleResponse.setYear( vehicle.getYear() );

        return vehicleResponse;
    }
}
