package com.bcp.training.rest;

import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;

import com.bcp.training.model.Expense;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/expenses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Expense Management", description = "Operations for managing expenses")
public class ExpenseResource {

    @GET
    // TODO 2: Add pagination and sort by "amount" and "associateId".
    @Operation(
            summary = "Get all expenses",
            description = "Retrieve a list of all expenses in the system"
    )
    @APIResponse(
            responseCode = "200",
            description = "Successful retrieval of expenses list"
    )
    public List<Expense> list() {
        return Expense.listAll();
    }

    @POST
    @Transactional
    public Expense create(final Expense expense) {

        Expense newExpense = Expense.of(expense.name, expense.paymentMethod, expense.amount.toString(), expense.associateId);

        newExpense.persist();

        return newExpense;
    }

    @DELETE
    @Path("{uuid}")
    @Transactional
    public void delete(@PathParam("uuid") final UUID uuid) {
        long numExpensesDeleted = Expense.delete("uuid", uuid);
        if (numExpensesDeleted == 0) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

    }

    @PUT
    @Transactional
    public void update(final Expense expense) {
        try {
            Expense.update(expense);
        } catch (RuntimeException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
