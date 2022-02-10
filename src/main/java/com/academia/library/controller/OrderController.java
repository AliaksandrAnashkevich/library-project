package com.academia.library.controller;

import com.academia.library.dto.OrderRequest;
import com.academia.library.dto.OrderResponse;
import com.academia.library.model.OrderStatus;
import com.academia.library.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get orders", description = "Get all orders")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @Operation(summary = "Get order", description = "Get order by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @GetMapping("{id}")
    public OrderResponse findById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @Operation(summary = "Create order", description = "Create new order")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "400", description = "Conflict", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public OrderResponse create(@RequestBody OrderRequest orderRequest,
                                @AuthenticationPrincipal UserDetails userDetails) {
        return orderService.create(userDetails.getUsername(), orderRequest);
    }

    @Operation(summary = "Update order", description = "Update order by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "400", description = "Conflict", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public OrderResponse update(@PathVariable("id") Long id,
                                @RequestBody OrderRequest orderRequest) {
        return orderService.update(id, orderRequest);
    }

    @Operation(summary = "Update order", description = "Update tag status to paid by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "400", description = "Conflict", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @PutMapping("paid/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public OrderResponse updateStatusToPaid(@PathVariable("id") Long id) {
        return orderService.updateStatus(id, OrderStatus.PAID, OrderStatus.DRAFT);
    }

    @Operation(summary = "Update order", description = "Update tag status to delivered by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "400", description = "Conflict", content = @Content)
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @PutMapping("delivered/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public OrderResponse updateStatusToDelivered(@PathVariable("id") Long id) {
        return orderService.updateStatus(id, OrderStatus.DELIVERED, OrderStatus.PAID);
    }

    @Operation(summary = "Delete order", description = "Delete order by id")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void delete(@PathVariable("id") Long id) {
        orderService.delete(id);
    }
}
